/**
 * Commands.java
 *
 * Copyright (c) by you
 *
 */
package com.api;

/**
 * 
 *
 * @date 2012-6-20
 * @author you
 *
 * @版本更新列表
 * 
 *         <pre>
 * 修改版本: 1.0.0
 * 修改日期：2012-6-20
 * 修改人 : you
 * 修改说明：形成初始版本
 *         </pre>
 */
public interface CommandsLotto {

	public static final String success = "0";
	//////////////////// html5 api 100 ~ 120 ///////////////////////////////
	public static final int Login = 101;

	/** query open draw list */
	public static final int QueryOpenDrawList = 102;

	/** query game market list */
	public static final int QueryGameMarketList = 103;

	/** query game odds & limit by type */
	public static final int QueryGameSettingByType = 105;

	/** query all odds info */
	public static final int QueryGameSettingAll = 106;

	/** place bet */
	public static final int PlaceBet = 109;

	/** 追号 */
	public static final int PlaceChase = 108;

	/** 批量提交 bet */
	public static final int PlaceBetBatch = 107;

	/** 批量 追号 */
	public static final int PlaceChaseBatch = 119;

	/** cancel bet */
	public static final int CancelBet = 110;

	/** cancel chase */
	public static final int CancelChase = 116;

	/** query result info */
	public static final int QueryResultList = 111;

	/** query result for trend */
	public static final int QueryTrendList = 112;

	/** bet history */
	public static final int ReportBetHistory = 113;

	/** chase history */
	public static final int ReportChaseHistory = 114;

	/** Chase detail */
	public static final int ReportChaseDetail = 115;

	public static final int PaperQuestion = 121;
	public static final int PaperAnswer = 122;

	/**** open bet list Page (outstanding) **/
	public static final int QueryBetListPage = 123;

	/** query result report list Page */
	public static final int ReportResultPage = 124;

	/** query result report draw today ticket **/
	public static final int ReportDrawTodayTicket = 125;

}
