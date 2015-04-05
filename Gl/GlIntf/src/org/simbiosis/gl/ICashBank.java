package org.simbiosis.gl;

import java.util.Date;
import java.util.List;

import org.simbiosis.gl.model.BankTrans;
import org.simbiosis.gl.model.CashTrans;

public interface ICashBank {

	//
	String getMaxCashTransCode(long company, long branch, String prefixCode);

	long saveCashTrans(CashTrans trans);

	CashTrans getCashTrans(long id);

	List<CashTrans> listCashTransByDate(long company, long branch,
			Date dateBegin, Date dateEnd, String strSyarat);

	void removeCashTrans(long id);

	void releaseCashTrans(CashTrans cbTrans);

	//
	String getMaxBankTransCode(long company, long branch, String prefixCode);

	long saveBankTrans(BankTrans trans);

	BankTrans getBankTrans(long id);

	List<BankTrans> listBankTransByDate(long company, long branch,
			Date dateBegin, Date dateEnd, String strSyarat);

	void removeBankTrans(long id);

	void releaseBankTrans(BankTrans cbTrans);

}
