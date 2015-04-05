package org.simbiosis.bp.gl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.gl.ICashBank;
import org.simbiosis.gl.ICoa;
import org.simbiosis.gl.ILedger;
import org.simbiosis.gl.model.BankTrans;
import org.simbiosis.gl.model.BankTransItem;
import org.simbiosis.gl.model.CashTrans;
import org.simbiosis.gl.model.CashTransItem;
import org.simbiosis.gl.model.Coa;
import org.simbiosis.gl.model.GlTrans;
import org.simbiosis.gl.model.GlTransItem;
import org.simbiosis.system.ISystem;
import org.simbiosis.system.UserDto;

@Stateless
@Remote(ICashBankBp.class)
public class CashBankBp implements ICashBankBp {

	@EJB(lookup = "java:global/SystemEar/SystemEjb/SystemImpl")
	ISystem iSystem;
	@EJB(lookup = "java:global/GlEar/GlEjb/CoaImpl")
	ICoa iCoa;
	@EJB(lookup = "java:global/GlEar/GlEjb/LedgerImpl")
	ILedger iLedger;
	@EJB(lookup = "java:global/GlEar/GlEjb/CashBankImpl")
	ICashBank iCashBank;

	String fillers[] = { "", "0", "00", "000", "0000" };
	DateTimeFormatter dtf = DateTimeFormat.forPattern("MMyy");

	String createCashCode(long company, long branch, Date date,
			String prefixCode) {
		// Buat kode baru
		String dateStr = dtf.print(new DateTime(date));
		String myPrefixCode = prefixCode + "/" + dateStr;
		// Cari dulu yang sudah ada
		String code = iCashBank.getMaxCashTransCode(company, branch,
				myPrefixCode);
		if (code != null) {
			String allCode[] = code.split("/");
			int number = Integer.parseInt(allCode[allCode.length - 1]) + 1;
			String numberCode = "" + number;
			code = fillers[5 - numberCode.length()] + numberCode;
		} else {
			code = fillers[4] + "1";
		}
		return myPrefixCode + "/" + code;
	}

	long saveGLTransByUser(UserDto user, GlTrans glTrans) {
		glTrans.setCompany(user.getCompany());
		glTrans.setBranch(user.getBranch());
		return iLedger.saveGLTrans(glTrans);
	}

	@Override
	public List<CashTrans> listCashTransByDate(String key, Date begin,
			Date end, int status) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			String strSyarat = "";
			switch (status) {
			case 1: // Transaksi cash bank belum released
				strSyarat = "and cbTrans.released=0";
				break;
			case 2: // Transaksi cash bank sudah released
				strSyarat = "and cbTrans.released=1";
				break;
			default:
				strSyarat = "";
			}
			return iCashBank.listCashTransByDate(user.getCompany(),
					user.getBranch(), begin, end, strSyarat);
		}
		return new ArrayList<CashTrans>();
	}

	@Override
	public long saveCashTrans(String key, CashTrans cbTrans) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			cbTrans.setCompany(user.getCompany());
			cbTrans.setBranch(user.getBranch());
			// Create code if baru
			if (cbTrans.getId() == 0) {
				Coa coa = iCoa.get(cbTrans.getCoa().getId());
				cbTrans.setCode(createCashCode(user.getCompany(),
						user.getBranch(), cbTrans.getDate(), coa.getPrefix()));
			}
			return iCashBank.saveCashTrans(cbTrans);
		}
		return 0;
	}

	@Override
	public CashTrans getCashTrans(long id) {
		return iCashBank.getCashTrans(id);
	}

	@Override
	public void removeCashTrans(long id) {
		iCashBank.removeCashTrans(id);
	}

	@Override
	public void releaseCashTrans(String key, List<Long> idList, int released) {
		UserDto user = iSystem.getUserFromSession(key);
		Date releaseDate = new Date();
		if (user != null) {
			for (Long id : idList) {
				CashTrans cbTrans = iCashBank.getCashTrans(id);
				CashTrans cbRelease = new CashTrans();
				cbRelease.setId(id);
				cbRelease.setReleased(released);
				if (released == 1) {
					// Buat ledger glnya...
					GlTrans glTrans = new GlTrans();
					glTrans.setType(1); // cash bank
					glTrans.setCode(cbTrans.getCode());
					glTrans.setDate(cbTrans.getDate());
					glTrans.setDescription(cbTrans.getDescription());
					glTrans.setCurrencyId(cbTrans.getCurrencyId());
					glTrans.setRate(cbTrans.getRate());
					glTrans.setReleased(1); // released
					glTrans.setReleasedBy(user.getId());
					glTrans.setReleaseDate(releaseDate);
					glTrans.setCompany(user.getCompany());
					glTrans.setBranch(user.getBranch());
					GlTransItem item1 = new GlTransItem();
					item1.setDescription(cbTrans.getDescription());
					item1.setDirection(cbTrans.getDirection());
					item1.setCoa(cbTrans.getCoa());
					glTrans.getItems().add(item1);
					double value = 0;
					for (CashTransItem cbItem : cbTrans.getItems()) {
						GlTransItem item = new GlTransItem();
						item.setDescription(cbItem.getDescription());
						item.setDirection(cbTrans.getDirection() == 1 ? 2 : 1);
						item.setCoa(cbItem.getCoa());
						item.setValue(cbItem.getValue());
						value += cbItem.getValue();
						glTrans.getItems().add(item);
					}
					item1.setValue(value);
					long idTrans = saveGLTransByUser(user, glTrans);
					// Release cb
					cbRelease.setGlTransId(idTrans);
					cbRelease.setReleasedBy(user.getId());
					cbRelease.setReleaseDate(releaseDate);
				} else {
					// Hapus ledger glnya...
					iLedger.removeGLTrans(cbTrans.getGlTransId());
					// UnRelease cb
					cbRelease.setGlTransId(0);
					cbRelease.setReleasedBy(0);
					cbRelease.setReleaseDate(releaseDate);
				}
				iCashBank.releaseCashTrans(cbRelease);
			}
		}
	}

	String createBankCode(long company, long branch, Date date,
			String prefixCode) {
		// Buat kode baru
		String dateStr = dtf.print(new DateTime(date));
		String myPrefixCode = prefixCode + "/" + dateStr;
		// Cari dulu yang sudah ada
		String code = iCashBank.getMaxCashTransCode(company, branch,
				myPrefixCode);
		if (code != null) {
			String allCode[] = code.split("/");
			int number = Integer.parseInt(allCode[allCode.length - 1]) + 1;
			String numberCode = "" + number;
			code = fillers[5 - numberCode.length()] + numberCode;
		} else {
			code = fillers[4] + "1";
		}
		return myPrefixCode + "/" + code;
	}

	@Override
	public List<BankTrans> listBankTransByDate(String key, Date begin,
			Date end, int status) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			String strSyarat = "";
			switch (status) {
			case 1: // Transaksi cash bank belum released
				strSyarat = "and cbTrans.released=0";
				break;
			case 2: // Transaksi cash bank sudah released
				strSyarat = "and cbTrans.released=1";
				break;
			default:
				strSyarat = "";
			}
			return iCashBank.listBankTransByDate(user.getCompany(),
					user.getBranch(), begin, end, strSyarat);
		}
		return new ArrayList<BankTrans>();
	}

	@Override
	public long saveBankTrans(String key, BankTrans cbTrans) {
		UserDto user = iSystem.getUserFromSession(key);
		if (user != null) {
			cbTrans.setCompany(user.getCompany());
			cbTrans.setBranch(user.getBranch());
			// Create code if baru
			if (cbTrans.getId() == 0) {
				Coa coa = iCoa.get(cbTrans.getCoa().getId());
				cbTrans.setCode(createCashCode(user.getCompany(),
						user.getBranch(), cbTrans.getDate(), coa.getPrefix()));
			}
			return iCashBank.saveBankTrans(cbTrans);
		}
		return 0;
	}

	@Override
	public BankTrans getBankTrans(long id) {
		return iCashBank.getBankTrans(id);
	}

	@Override
	public void removeBankTrans(long id) {
		iCashBank.removeBankTrans(id);
	}

	@Override
	public void releaseBankTrans(String key, List<Long> idList, int released) {
		UserDto user = iSystem.getUserFromSession(key);
		Date releaseDate = new Date();
		if (user != null) {
			for (Long id : idList) {
				BankTrans cbTrans = iCashBank.getBankTrans(id);
				BankTrans cbRelease = new BankTrans();
				cbRelease.setId(id);
				cbRelease.setReleased(released);
				if (released == 1) {
					// Buat ledger glnya...
					GlTrans glTrans = new GlTrans();
					glTrans.setType(1); // cash bank
					glTrans.setCode(cbTrans.getCode());
					glTrans.setDate(cbTrans.getDate());
					glTrans.setDescription(cbTrans.getDescription());
					glTrans.setCurrencyId(cbTrans.getCurrencyId());
					glTrans.setRate(cbTrans.getRate());
					glTrans.setReleased(1); // released
					glTrans.setReleasedBy(user.getId());
					glTrans.setReleaseDate(releaseDate);
					glTrans.setCompany(user.getCompany());
					glTrans.setBranch(user.getBranch());
					GlTransItem item1 = new GlTransItem();
					item1.setDescription(cbTrans.getDescription());
					item1.setDirection(cbTrans.getType());
					item1.setCoa(cbTrans.getCoa());
					glTrans.getItems().add(item1);
					double value = 0;
					for (BankTransItem cbItem : cbTrans.getItems()) {
						GlTransItem item = new GlTransItem();
						item.setDescription(cbItem.getDescription());
						item.setDirection(cbTrans.getType() == 1 ? 2 : 1);
						item.setCoa(cbItem.getCoa());
						item.setValue(cbItem.getValue());
						value += cbItem.getValue();
						glTrans.getItems().add(item);
					}
					item1.setValue(value);
					long idTrans = saveGLTransByUser(user, glTrans);
					// Release cb
					cbRelease.setGlTransId(idTrans);
					cbRelease.setReleasedBy(user.getId());
					cbRelease.setReleaseDate(releaseDate);
				} else {
					// Hapus ledger glnya...
					iLedger.removeGLTrans(cbTrans.getGlTransId());
					// UnRelease cb
					cbRelease.setGlTransId(0);
					cbRelease.setReleasedBy(0);
					cbRelease.setReleaseDate(releaseDate);
				}
				iCashBank.releaseBankTrans(cbRelease);
			}
		}
	}

}
