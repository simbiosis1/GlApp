package org.simbiosis.bp.gl;

import java.util.Date;
import java.util.List;

import org.simbiosis.gl.model.BankTrans;
import org.simbiosis.gl.model.CashTrans;

public interface ICashBankBp {

	//
	List<CashTrans> listCashTransByDate(String key, Date begin, Date end,
			int status);

	long saveCashTrans(String key, CashTrans cbTrans);

	CashTrans getCashTrans(long id);

	void removeCashTrans(long id);

	void releaseCashTrans(String key, List<Long> idList, int released);

	//
	List<BankTrans> listBankTransByDate(String key, Date begin, Date end,
			int status);

	long saveBankTrans(String key, BankTrans cbTrans);

	BankTrans getBankTrans(long id);

	void removeBankTrans(long id);

	void releaseBankTrans(String key, List<Long> idList, int released);

}
