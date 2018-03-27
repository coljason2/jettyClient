
package com.api;

public interface Commands {

	//////////////////// general api 0 ~ 20 ///////////////////////////////
	public static final int Login = 1;
	public static final int Logout = 2;

	/** qyery acct info */
	public static final int QueryAcctInfo = 3;

	/** query server time */
	public static final int ServerTime = 6;

	/** query announcement */
	public static final int QueryNotice = 7;

	/** user online time */
	public static final int OnLineTime = 8;

	////////////////// game api 20 ~ 50 ///////////////////////////////

	/** query draw info */
	public static final int QueryDrawInfo = 20;

	/** query all odds info */
	public static final int QueryOdds = 21;

	/** query game limit */
	public static final int QueryGameLimit = 22;

	/** query result info */
	public static final int QueryResultList = 30;

	/** query result report list */
	public static final int ReportResult = 37;

	/** place bet */
	public static final int PlaceBet = 40;

	/** re bet */
	public static final int ReBet = 41;

	/**** open bet list Page (outstanding) **/
	public static final int QueryBetListPage = 123;

	/** query result report list Page */
	public static final int ReportResultPage = 124;

	/** query result report draw today ticket **/
	public static final int ReportDrawTodayTicket = 125;

	////////////////// notify/push api -1 ~ -50 ///////////////////////////////
	/** kick out user */
	public static final int Kickout = -1;

	/** acct info update */
	public static final int AcctInfoUpdated = -3;

	/** push notice */
	public static final int PushNotice = -7;

	/** market open */
	public static final int MarketOpen = -9;

	/** market closed */
	public static final int MarketClosed = -10;

	/** draw close */
	public static final int DrawClosed = -11;

	/** draw award */
	public static final int DrawAward = -12;

}
