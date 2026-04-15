package hy.ea.util;

/**
 * 视图工具
 *
 * @author zgzg
 */
public class VOtool {
    /**
     * 单据汇总查询
     *
     * @param val 1：用于列表显示的部分字段查询 2：查询全部字段 3: 待营销审核、待人事审核列表显示及导出字段 4：进销存采购单据、出库单据、入库单据
     * @return
     */
    public static String getCashierBillVO(int val) {
        String result = null;
        switch (val) {
            case 1:
                result = "select t.cashierBillsID,c.companyname,t.journalnum,t.billstype,co.organizationname as departmentname,"
                        + " cs.staffname,t.cashierdate,t.afterdiscount,t.bankdepotname,t.dataDepotName,cc.companyname as ccompanyname,"
                        + " t.accountnum,t.contactconnections,cu.staffname as  contactusername,t.useraccountnum,t.phone,"
                        + " t.responsible,t.accountant,t.cashier,"
                        + " case when t.status ='00' then '草稿' "
                        + " when t.status='01' then '待主管审核'"
                        + " when t.status='02' then '待会计审核'"
                        + " when t.status='03' then '待出纳审核'"
                        + " when t.status='04' then '已审核'"
                        + " when t.status='20' then '税务单据'"
                        + " when t.status='30' then '未审核作废'"
                        + " when t.status is null then ''"
                        + " else '驳回作废' end,"
                        + " case when t.depstatue='01' then '已转科一未收集'"
                        + " when t.depstatue='02' then '已转科一已收集'"
                        + " else '未转科一' end,"
                        + " t.marketer,t.personnel,t.input"
                        + " from dtcashierbills t"
                        + " left join  dtcontactcompany cc on  cc.ccompanyid= t.ccompanyid"
                        + " left join dt_hr_staff cu on  cu.staffid = t.contactuserid"
                        + " left join dtcompany c on  c.companyid = t.companyid"
                        + " left join dtcorganization co on  co.organizationid = t.departmentid"
                        + " left join dt_hr_staff cs on  cs.staffid = t.staffid";
                break;
            case 2:
                // 税务单据查询
                result = "select t.cashierBillsID,c.companyname,t.journalnum,t.billstype,co.organizationname as departmentname,"
                        + " cs.staffname,t.input,t.cashierdate,t.afterdiscount,t.bankdepotname,t.dataDepotName,cc.companyname as ccompanyname,"
                        + " t.accountnum,t.contactconnections,cu.staffname as  contactusername,t.useraccountnum,t.phone,t.taxDate,"
                        + " t.competent,t.manager,t.financial,"
                        + " case when t.taxstatus='00' then '草稿' "
                        + " when t.taxstatus='01' then '待主管审核' "
                        + " when t.taxstatus='02' then '待经理审核'"
                        + " when t.taxstatus='03' then '待财务审计审核'"
                        + " when t.taxstatus='04' then '已报税'"
                        + " else '驳回作废' end"
                        + " from dtcashierbills t"
                        + " left join  dtcontactcompany cc on  cc.ccompanyid= t.ccompanyid"
                        + " left join dt_hr_staff cu on  cu.staffid = t.contactuserid"
                        + " left join dtcompany c on  c.companyid = t.companyid"
                        + " left join dtcorganization co on  co.organizationid = t.departmentid"
                        + " left join dt_hr_staff cs on  cs.staffid = t.staffid";
                break;
            case 3: // 咨询跟踪单、待营销审核、待人事审核列表显示及导出字段
                result = "select t.cashierBillsID,c.companyname,t.journalnum,t.billstype,co.organizationname as departmentname,"
                        + "cs.staffname,t.input,t.cashierdate,cc.companyname as ccompanyname,t.accountnum,t.contactconnections,"
                        + "cu.staffname as  contactusername,t.useraccountnum,t.phone,t.marketer,t.personnel,"
                        + "case when t.consultStatus='00' then '草稿' "
                        + "when t.consultStatus='01' then '待营销审核' "
                        + "when t.consultStatus='02' then '待人事审核' "
                        + "when t.consultStatus='03' then '已审核' "
                        + "else '驳回作废' end "
                        + "from dtcashierbills t "
                        + "left join  dtcontactcompany cc on  cc.ccompanyid= t.ccompanyid "
                        + "left join dt_hr_staff cu on cu.staffid = t.contactuserid "
                        + "left join dtcompany c on  c.companyid = t.companyid "
                        + "left join dtcorganization co on  co.organizationid = t.departmentid "
                        + "left join dt_hr_staff cs on  cs.staffid = t.staffid";
                break;

            case 4: //进销存采购单据、出库单据、入库单据
                result = "select f.financialbillID, c.companyname, f.journalNum, f.billsType, cr.organizationname,"
                        + " f.StaffName, f.billStaffName, f.billsDate, f.companyBankNum, f.ccompanyName,"
                        + " f.ccompanyRelationship, f.ccompanyTel, f.cstaffName,"
                        + " case when f.billStatus='12' then '未收货'"
                        + " when f.billStatus='13' then '已收货'"
                        + " when f.billStatus='14' then '已验货'"
                        + " when f.billStatus='15' then '已入库'"
                        + " when f.billStatus='16' then '已出库'"
                        + " when f.billStatus='19' then '销售出库'"
                        + " when f.billStatus='04' then '待主管审核'"
                        + " when f.billStatus='07' then '已三审通过'"
                        + " when f.billStatus='05' then '审核中'"
                        + " when f.billStatus='22' then '草稿'"
                        + " else '' end,f.staffsName"
                        + " from dt_inv_fb f"
                        + " left join dtcompany c on  c.companyid = f.companyID"
                        + " left join dtcorganization cr on  cr.organizationid = f.departmentID"
                        + " left join dtCashierBills cs on f.cashierBillsID=cs.cashierBillsID";
                break;
            case 5:
                result = "select t.cashierBillsID,c.companyname,t.journalnum,t.billstype,co.organizationname as departmentname,"
                        + " cs.staffname,t.cashierdate,t.afterdiscount,t.bankdepotname,t.dataDepotName,cc.companyname as ccompanyname,"
                        + " t.accountnum,t.contactconnections,cu.staffname as  contactusername,t.useraccountnum,t.phone,"
                        + " t.responsible,t.accountant,t.cashier,"
                        + " case  when t.status='04' then '已审核'"
                        + " else '' end,"
                        + " case when t.depstatue='01' then '已转科一未收集'"
                        + " when t.depstatue='02' then '已转科一已收集'"
                        + " else '未转科一' end,"
                        + " t.marketer,t.personnel,t.input"
                        + " from dt_history_cashierbills t"
                        + " left join  dtcontactcompany cc on  cc.ccompanyid= t.ccompanyid"
                        + " left join dt_hr_staff cu on  cu.staffid = t.contactuserid"
                        + " left join dtcompany c on  c.companyid = t.companyid"
                        + " left join dtcorganization co on  co.organizationid = t.departmentid"
                        + " left join dt_hr_staff cs on  cs.staffid = t.staffid";
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 物品明细汇总查询
     *
     * @param val 1:用于列表显示的部分字段查询 2：查询全部字段
     * @return
     */
    public static String getGoodsBillVO(int val) {
        String result = "";
        switch (val) {
            case 1:
                result = "select good.goodsbillsid,c.companyname,t.journalnum,t.billstype,co.organizationname departmentname,"
                        + "cs.staffname,t.cashierdate,cc.goodsname,cc.goodscoding,cc.typeid,cc.variableid,good.price,good.quantity,"
                        + "good.money,good.direction,good.balance,"
                        + "case when t.taxstatus = '01' then '待主管审核'"
                        + " when t.taxstatus = '02' then '待经理审核'"
                        + " when t.taxstatus = '03' then '待财务审计审核'"
                        + " when t.taxstatus = '04' then '可报税'"
                        + " when t.taxstatus = '10' then '驳回作废'"
                        + " else '' end,"
                        + " case when t.status = '00' then '草稿'"
                        + " when t.status = '01' then '待主管审核'"
                        + " when t.status = '02' then '待会计审核'"
                        + " when t.status = '03' then '待出纳审核'"
                        + " when t.status = '04' then '已审核'"
                        + " when t.status = '20' then '税务单据'"
                        + " else '驳回作废' end"
                        + " from dtgoodsbills good"
                        + " left join dtgoodsmanage cc on cc.goodsID = good.goodsID"
                        + " left join dtcashierbills t on t.cashierbillsid= good.cashierbillsid"
                        + " left join dtcompany c on c.companyid = t.companyid"
                        + " left join dtcorganization co on co.organizationid = t.departmentid"
                        + " left join dt_hr_staff cs on cs.staffid = t.staffid";
                break;
        /*
         * case 2: result = "select" + " s.startdate,
		 * s.enddate,s.perioddate,s.batchnumber,cc.goodscoding,cc.defaultstorage,cc.goodsname,cc.typeid,cc.mnemoniccode," + "
		 * cc.model,cc.standard,s.identifyingcode,s.goodsvariableid,s.quantity,s.boxstandard,s.pricemanage,s.price,s.money,s.costtype," + "
		 * s.subjectsname,s.depotname,s.loan,s.forloan,s.direction,s.remindedcontent,s.paytype,s.logistics,s.moneyspent" + "
		 * from dtGoodsBills s,dtgoodsmanage cc ," + "(select" + "
		 * c.companyname,co.organizationname as
		 * departmentname,cc.cresponsible,cu.staffname as contactusername,d.*" + "
		 * from dtcashierbills d" + " left join dtcontactcompany cc on
		 * cc.ccompanyid= d.ccompanyid" + " left join dt_hr_staff cu on
		 * cu.staffid = d.contactuserid" + " left join dtcompany c on
		 * c.companyid = d.companyid" + " left join dtcorganization co on
		 * co.organizationid = d.departmentid" + " left join dt_hr_staff cs on
		 * cs.staffid = d.staffid) t" + " where s.cashierbillsid
		 * =t.cashierbillsid and cc.goodsID = s.goodsID" + " and t.companyid = ?
		 * and t.journalnum=?"; break;
		 */
            default:
                break;
        }
        return result;
    }

    /**
     * 获取工资打印hql视图查询
     *
     * @return
     */
    public static String getWagesHql() {
        String result = "select new SalaryIntegral(max(j.logBookKey),max(j.companyName),j.staffID,max(j.staffName),max(j.categoryname),max(j.postname),"
                + "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000003' then j.bisect else '0' end) ,"
                + // 职务职责
                "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000002' then j.bisect else '0' end) ,"
                + // 基本积分
                "sum(case when j.scoreSort = 'scode20120525f865m47gmi0000000002' then j.bisect else '0' end) ,"
                + // 目标任务考核计分
                "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000004' then j.bisect else '0' end) ,"
                + // 周末加班积分
                "sum(case when j.scoreSort = 'scode201202157awfwsxchm0000000003' then j.bisect else '0' end) ,"
                + // 晚上加班积分
                "sum(case when j.scoreSort = 'scode201202157awfwsxchm0000000004' then j.bisect else '0' end) ,"
                + // 节假日加班积分
                "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000006' then j.bisect else '0' end) ,"
                + // 月考评积分
                //"sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000028' then j.bisect else '0' end) ,"
                //+ // 计件得分
                "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000031' then j.bisect else '0' end) ,"
                + // 奖励积分
                "sum(case when j.scoreSort = 'scode201202157awfwsxchm0000000005' then j.bisect else '0' end) ,"
                + // 特殊人才积分
                "sum(case when j.scoreSort = 'scode201202157awfwsxchm0000000006' then j.bisect else '0' end) ,"
                + // 保密工资积分
                "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000030' then j.bisect else '0' end) ,"
                + // 安全得分
                "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000029' then j.bisect else '0' end) ,"
                + // 任务得分
                //"sum(case when j.scoreSort = 'scode20120223p2a22556py0000000002' then j.bisect else '0' end) ,"
                //+ // 补助话费
                //"sum(case when j.scoreSort = 'scode20120223p2a22556py0000000003' then j.bisect else '0' end) ,"
                //+ // 生活补助
                "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000032' then j.bisect else '0' end) ,"
                + // 个人所得税
                "sum(case when j.scoreSort = 'scode201202157awfwsxchm0000000007' then j.bisect else '0' end) ,"
                + // 个人应交社保
                "sum(case when j.scoreSort = 'scode201202157awfwsxchm0000000008' then j.bisect else '0' end) ,"
                + // 个人应交公积金
                //"sum(case when j.scoreSort = 'scode201202157awfwsxchm0000000009' then j.bisect else '0' end) ,"
                //+ // 扣电话费
                //"sum(case when j.scoreSort = 'scode201202157awfwsxchm0000000010' then j.bisect else '0' end) ,"
                //+ // 扣生活费
                "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000033' then j.bisect else '0' end) ,"
                + // 违规折扣
                "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000034' then j.bisect else '0' end) ,"
                + // 任务折扣
                "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000035' then j.bisect else '0' end) ,"
                + // 暂扣安全积分
                "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000036' then j.bisect else '0' end) ,"
                + // 考勤折扣
                "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000005' then j.bisect else '0' end) ,"
                + // 公司承担积分;

                "sum(case when j.scoreSort = 'scode20140116jjp47y7tv80000000002' then j.bisect else '0' end) ,"
                + // 孝道金;
                "sum(case when j.scoreSort = 'scode20140116jjp47y7tv80000000003' then j.bisect else '0' end) ,"
                + // 竞职金;
                "sum(case when j.scoreSort = 'scode20140116jjp47y7tv80000000006' then j.bisect else '0' end) ,"
                + // 生活补助;
                "sum(case when j.scoreSort = 'scode20140116jjp47y7tv80000000005' then j.bisect else '0' end) ) "
                + // 通话补助;
//				"sum(case when j.scoreSort = 'scode20140116jjp47y7tv80000000004' then j.bisect else '0' end) ) "
//				+ // pk金;

                "from LogBookSummary j";
        return result;
    }

    /**
     * 获取工资打印hql视图查询 按月份分组
     *
     * @return
     */
    public static String getWagesHqlX() {
        String result = "select new SalaryIntegral(to_char(j.todaydate, 'yyyy-MM'),max(j.companyName),j.staffID,max(j.staffName), max(j.categoryname),max(j.postname),"
                + "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000003' then j.bisect else '0' end) ,"
                + // 职务职责
                "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000002' then j.bisect else '0' end) ,"
                + // 基本积分
                "sum(case when j.scoreSort = 'scode20120525f865m47gmi0000000002' then j.bisect else '0' end) ,"
                + // 目标任务考核计分
                "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000004' then j.bisect else '0' end) ,"
                + // 周末加班积分
                "sum(case when j.scoreSort = 'scode201202157awfwsxchm0000000003' then j.bisect else '0' end) ,"
                + // 晚上加班积分
                "sum(case when j.scoreSort = 'scode201202157awfwsxchm0000000004' then j.bisect else '0' end) ,"
                + // 节假日加班积分
                "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000006' then j.bisect else '0' end) ,"
                + // 月考评积分
                //"sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000028' then j.bisect else '0' end) ,"
                //+ // 计件得分
                "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000031' then j.bisect else '0' end) ,"
                + // 奖励积分
                "sum(case when j.scoreSort = 'scode201202157awfwsxchm0000000005' then j.bisect else '0' end) ,"
                + // 特殊人才积分
                "sum(case when j.scoreSort = 'scode201202157awfwsxchm0000000006' then j.bisect else '0' end) ,"
                + // 保密工资积分
                "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000030' then j.bisect else '0' end) ,"
                + // 安全得分
                "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000029' then j.bisect else '0' end) ,"
                + // 任务得分
                //"sum(case when j.scoreSort = 'scode20120223p2a22556py0000000002' then j.bisect else '0' end) ,"
                //+ // 补助话费
                //"sum(case when j.scoreSort = 'scode20120223p2a22556py0000000003' then j.bisect else '0' end) ,"
                //+ // 生活补助
                "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000032' then j.bisect else '0' end) ,"
                + // 个人所得税
                "sum(case when j.scoreSort = 'scode201202157awfwsxchm0000000007' then j.bisect else '0' end) ,"
                + // 个人应交社保
                "sum(case when j.scoreSort = 'scode201202157awfwsxchm0000000008' then j.bisect else '0' end) ,"
                + // 个人应交公积金
                //"sum(case when j.scoreSort = 'scode201202157awfwsxchm0000000009' then j.bisect else '0' end) ,"
                //+ // 扣电话费
                //"sum(case when j.scoreSort = 'scode201202157awfwsxchm0000000010' then j.bisect else '0' end) ,"
                //+ // 扣生活费
                "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000033' then j.bisect else '0' end) ,"
                + // 违规折扣
                "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000034' then j.bisect else '0' end) ,"
                + // 任务折扣
                "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000035' then j.bisect else '0' end) ,"
                + // 暂扣安全积分
                "sum(case when j.scoreSort = 'scode20100812ikdv89y6kt0000000036' then j.bisect else '0' end) ,"
                + // 考勤折扣
                "sum(case when j.scoreSort = 'scode201007306kdf8m76me0000000005' then j.bisect else '0' end) ,"
                + // 公司承担积分;

                "sum(case when j.scoreSort = 'scode20140116jjp47y7tv80000000002' then j.bisect else '0' end) ,"
                + // 孝道金;
                "sum(case when j.scoreSort = 'scode20140116jjp47y7tv80000000003' then j.bisect else '0' end) ,"
                + // 竞职金;
                "sum(case when j.scoreSort = 'scode20140116jjp47y7tv80000000006' then j.bisect else '0' end) ,"
                + // 生活补助;
                "sum(case when j.scoreSort = 'scode20140116jjp47y7tv80000000005' then j.bisect else '0' end) ) "
                + // 通话补助;
//				"sum(case when j.scoreSort = 'scode20140116jjp47y7tv80000000004' then j.bisect else '0' end) ) "
//				+ // pk金;

                "from LogBookSummary j";
        return result;
    }

    /**
     * 获取离职员工工资打印hql视图查询
     *
     * @return
     */
    public static String getDimissionWagesHql() {
        String results = getWagesHql().toString();
        String result = results.substring(0, results.indexOf("from"));
        result += "from DimissionLogBookSummary j";
        return result;
    }

    /**
     * 获取离职员工工资打印hql视图查询 按月份分组
     *
     * @return
     */
    public static String getDimissionWagesHqlX() {
        String results = getWagesHqlX().toString();
        String result = results.substring(0, results.indexOf("from"));
        result += "from DimissionLogBookSummary j";
        return result;
    }

    public static StringBuffer getsql(int val1, int val2, int val3) {
        StringBuffer result = new StringBuffer();
        switch (val1) {
            case 1: //查询佣金，成本价
                switch (val2) {
                    //成本，卖价，佣金
                    case 1: //零售价
                        switch (val3) {
                            case 1:
                                result.append("SELECT S.EF_PRICE,S.RE_PRICE,S.BROKERAGE,S.SUID,S.TZ_TYPE FROM DT_PRO_SETUP S WHERE S.PPID = ? AND S.COM_ID = ?");
                                break;
                            case 2: //主表
                                result.append("SELECT S.EF_PRICE,S.RE_PRICE,S.BROKERAGE,S.SUID,S.TZ_TYPE FROM DT_PRO_SETUP S WHERE S.SUID=?");
                                break;
                            case 3: //历史表
                                result.append("SELECT S.EF_PRICE,S.RE_PRICE,S.BROKERAGE,S.SUBID,S.TZ_TYPE FROM DT_PRO_SETUP_BACKUP S WHERE S.SUBID=?");
                                break;
                        }
                        break;
                    case 2: //批发价
                        switch (val3) {
                            case 2: //主表
                                result.append("SELECT W.FACTORY,W.WHOLESALE,W.BROKERAGE,W.WHOLESALEID,W.INVESTTYPE FROM DT_PRO_WHOLESALE W WHERE W.WHOLESALEID=?");
                                break;
                            case 3: //历史表
                                result.append("SELECT W.FACTORY,W.WHOLESALE,W.BROKERAGE,W.WHOLESALEID,W.INVESTTYPE FROM DT_PRO_WHO_HISTORY W WHERE W.WHOLESALEID=?");
                                break;
                        }
                        break;
                    case 3: //vip价
                        switch (val3) {
                            case 2: //主表
                                result.append("SELECT V.FACTORY,V.VIP,V.BROKERAGE,V.VIPID,V.INVESTTYPE FROM DT_PRO_VIP V WHERE V.VIPID=?");
                                break;
                            case 3: //历史表
                                result.append("SELECT V.FACTORY,V.VIP,V.BROKERAGE,V.VIPID,V.INVESTTYPE FROM DT_PRO_VIP_HISTORY V WHERE V.VIPID=?");
                                break;
                        }
                        break;
                    case 4: //活动价
                        result.append("SELECT PAP.FACTORY, PAP.ACTPRICE, PAP.BROKERAGE,PAP.ACTPRICEID,PAP.INVESTTYPE FROM DT_PRO_ACTIVITY_PRICE PAP");
                        result.append(" WHERE PAP.ACTPRICEID = ?");
                        break;
                    case 5: //活动价
                        result.append("SELECT PAP.FACTORY, PAP.ACTPRICE, PAP.BROKERAGE,PAP.ACTPRICEID,PAP.INVESTTYPE FROM DT_PRO_ACTIVITY_PRICE PAP");
                        result.append(" WHERE PAP.ACTPRICEID = ?");
                        break;
                }
                break;
            case 2:
                switch (val2) {
                    //代理
                    case 1: //零售价
                        switch (val3) {
                            case 1://主表
                                result.append("SELECT SUP.AMOUNT,SUP.TYPE_PPID,SUP.SUSID FROM DT_PRO_SETUP_SUB SUP WHERE SUP.SUID = ?");
                                break;
                            case 2: //历史表
                                result.append("SELECT SUP.AMOUNT,SUP.TYPE_PPID,SUP.SUSBID FROM DT_PRO_SETUP_SUB_BACKUP SUP WHERE SUP.SUBID=?");
                                break;
                        }
                        break;
                    case 2: //批发价
                        switch (val3) {
                            case 1: //主表
                                result.append("SELECT WHO.BROKERAGE,WHO.TYPEPPID,WHO.WHOBROID FROM DT_PRO_WHO_BROKERAGE WHO WHERE WHO.WHOLESALEID=?");
                                break;
                            case 2: //历史表
                                result.append("SELECT WHO.BROKERAGE,WHO.TYPEPPID,WHO.WHOBROID FROM DT_PRO_WHO_BRO_HISTORY WHO WHERE WHO.WHOLESALEID=?");
                                break;
                        }
                        break;
                    case 3: //vip价
                        switch (val3) {
                            case 1: //主表
                                result.append("SELECT PVB.BROKERAGE,PVB.TYPEPPID,PVB.VIPBROID FROM DT_PRO_VIP_BROKERAGE PVB WHERE PVB.VIPID=?");
                                break;
                            case 2: //历史表
                                result.append("SELECT PVB.BROKERAGE,PVB.TYPEPPID,PVB.VIPBROID FROM DT_PRO_VIP_BRO_HISTORY PVB WHERE PVB.VIPID=?");
                                break;
                        }
                        break;
                    case 4: //活动价
                        result.append("SELECT PAB.BROKERAGE,PAB.TYPEPPID,PAB.ACTIVITYBROID FROM DT_PRO_ACTIVITY_BROKERAGE PAB WHERE PAB.ACTPRICEID = ?");
                        break;
                    case 5: //活动价
                        result.append("SELECT PAB.BROKERAGE,PAB.TYPEPPID,PAB.ACTIVITYBROID FROM DT_PRO_ACTIVITY_BROKERAGE PAB WHERE PAB.ACTPRICEID = ?");
                        break;
                }
                break;
        }
        return result;
    }

    /**
     * 获取产品价格
     *
     * @param val1
     * @param val2
     * @param val3
     * @return
     */
    public static StringBuffer getBrokerageSql(int val1, int val2, int val3) {
        StringBuffer result = new StringBuffer();
        System.out.println(val1 + "," + val2 + "," + val3);
        switch (val1) {
            case 1: //查询佣金，成本价
                switch (val2) {
                    //成本，卖价，佣金
                    case 1: //零售价
                        switch (val3) {
                            case 1:
                                result.append("SELECT S.EF_PRICE,S.RE_PRICE,S.BROKERAGE FROM DT_PRO_SETUP S WHERE S.PPID = ? AND S.COM_ID = ?");
                                break;
                            case 2: //主表
                                result.append("SELECT S.EF_PRICE,S.RE_PRICE,S.BROKERAGE FROM DT_PRO_SETUP S WHERE S.SUID=?");
                                break;
                            case 3: //历史表
                                result.append("SELECT S.EF_PRICE,S.RE_PRICE,S.BROKERAGE FROM DT_PRO_SETUP_BACKUP S WHERE S.SUBID=?");
                                break;
                        }
                        break;
                    case 2: //批发价
                        switch (val3) {
                            case 2: //主表
                                result.append("SELECT W.FACTORY,W.WHOLESALE,W.BROKERAGE FROM DT_PRO_WHOLESALE W WHERE W.WHOLESALEID=?");
                                break;
                            case 3: //历史表
                                result.append("SELECT W.FACTORY,W.WHOLESALE,W.BROKERAGE FROM DT_PRO_WHO_HISTORY W WHERE W.WHOLESALEID=?");
                                break;
                        }
                        break;
                    case 3: //vip价
                        switch (val3) {
                            case 2: //主表
                                result.append("SELECT V.FACTORY,V.VIP,V.BROKERAGE FROM DT_PRO_VIP V WHERE V.VIPID=?");
                                break;
                            case 3: //历史表
                                result.append("SELECT V.FACTORY,V.VIP,V.BROKERAGE FROM DT_PRO_VIP_HISTORY V WHERE V.VIPID=?");
                                break;
                        }
                        break;
                    case 4: //活动价
                        result.append("SELECT PAP.FACTORY, PAP.ACTPRICE, PAP.BROKERAGE FROM DT_PRO_ACTIVITY_PRICE PAP");
                        result.append(" WHERE PAP.ACTPRICEID = ?");
                        break;
                    case 5: //活动价
                        result.append("SELECT PAP.FACTORY, PAP.ACTPRICE, PAP.BROKERAGE FROM DT_PRO_ACTIVITY_PRICE PAP");
                        result.append(" WHERE PAP.ACTPRICEID = ?");
                        break;
                }
                break;
            case 2: //已抢购除省县村代理以外的该产品
                switch (val2) {
                    case 1: //零售价
                        switch (val3) {
                            case 1: //主表
                                result.append("SELECT PRO.PPID,SUB.SCCID,SUP.AMOUNT,SUB.TYEPPPID");
                                result.append(" FROM DT_PRO_SETUP_SUB SUP,T_CUSCOM_PRODUCT PRO,T_ESHOP_CUSCOM_SUB SUB");
                                result.append(" WHERE SUB.SCCSID=PRO.SCCSID AND PRO.PPID=SUP.PPID AND SUP.AMOUNT > 0 AND SUP.TYPE_PPID IS NOT NULL ");
                                result.append(" AND SUP.TYPE_PPID=SUB.TYEPPPID AND SUB.TYEPPPID NOT IN(?,?,?) AND PRO.PPID= ?");
                                break;
                            case 2: //主表
                                result.append("SELECT PRO.PPID, SUB.SCCID, SUP.AMOUNT, SUB.TYEPPPID");
                                result.append(" FROM DT_PRO_SETUP_SUB SUP");
                                result.append(" LEFT JOIN T_CUSCOM_PRODUCT PRO ON PRO.PPID = SUP.PPID");
                                result.append(" LEFT JOIN T_ESHOP_CUSCOM_SUB SUB ON SUB.SCCSID = PRO.SCCSID AND SUP.TYPE_PPID = SUB.TYEPPPID");
                                result.append(" WHERE SUP.AMOUNT > 0 AND SUP.TYPE_PPID IS NOT NULL");
                                result.append(" AND SUB.TYEPPPID NOT IN (?, ?, ?) AND SUP.SUID = ?");
                                break;
                            case 3: //历史表
                                result.append("SELECT PRO.PPID, SUB.SCCID, SUP.AMOUNT, SUB.TYEPPPID");
                                result.append(" FROM DT_PRO_SETUP_SUB_BACKUP SUP");
                                result.append(" LEFT JOIN T_CUSCOM_PRODUCT PRO ON PRO.PPID = SUP.PPID");
                                result.append(" LEFT JOIN T_ESHOP_CUSCOM_SUB SUB ON SUB.SCCSID = PRO.SCCSID AND SUP.TYPE_PPID = SUB.TYEPPPID");
                                result.append(" WHERE SUP.AMOUNT > 0 AND SUP.TYPE_PPID IS NOT NULL");
                                result.append(" AND SUB.TYEPPPID NOT IN (?, ?, ?) AND SUP.SUBID = ?");
                                break;
                        }
                        break;
                    case 2: //批发价
                        switch (val3) {
                            case 2: //主表
                                result.append("SELECT PRO.PPID, SUB.SCCID, WHO.BROKERAGE, SUB.TYEPPPID");
                                result.append(" FROM DT_PRO_WHO_BROKERAGE WHO");
                                result.append(" LEFT JOIN DT_PRO_SETUP_SUB SUP ON WHO.PPID = SUP.PPID AND WHO.TYPEPPID=SUP.TYPE_PPID");
                                result.append(" LEFT JOIN T_CUSCOM_PRODUCT PRO ON PRO.PPID = SUP.PPID");
                                result.append(" LEFT JOIN T_ESHOP_CUSCOM_SUB SUB");
                                result.append(" ON SUP.TYPE_PPID = SUB.TYEPPPID AND SUB.SCCSID = PRO.SCCSID");
                                result.append(" WHERE WHO.BROKERAGE > 0 AND WHO.TYPEPPID IS NOT NULL");
                                result.append(" AND SUB.TYEPPPID NOT IN(?,?,?) AND WHO.WHOLESALEID=?");
                                break;
                            case 3: //历史表
                                result.append("SELECT PRO.PPID, SUB.SCCID, WHO.BROKERAGE, SUB.TYEPPPID");
                                result.append(" FROM DT_PRO_WHO_BRO_HISTORY WHO");
                                result.append(" LEFT JOIN DT_PRO_SETUP_SUB SUP ON WHO.PPID = SUP.PPID AND WHO.TYPEPPID=SUP.TYPE_PPID");
                                result.append(" LEFT JOIN T_CUSCOM_PRODUCT PRO ON PRO.PPID = SUP.PPID");
                                result.append(" LEFT JOIN T_ESHOP_CUSCOM_SUB SUB");
                                result.append(" ON SUP.TYPE_PPID = SUB.TYEPPPID AND SUB.SCCSID = PRO.SCCSID");
                                result.append(" WHERE WHO.BROKERAGE > 0 AND WHO.TYPEPPID IS NOT NULL");
                                result.append(" AND SUB.TYEPPPID NOT IN(?,?,?) AND WHO.WHOLESALEID=?");
                                break;
                        }
                        break;
                    case 3: //vip价
                        switch (val3) {
                            case 2: //主表
                                result.append("SELECT PRO.PPID, SUB.SCCID, PVB.BROKERAGE, SUB.TYEPPPID");
                                result.append(" FROM  DT_PRO_VIP_BROKERAGE PVB");
                                result.append(" LEFT JOIN DT_PRO_SETUP_SUB SUP ON PVB.PPID = SUP.PPID AND PVB.TYPEPPID = SUP.TYPE_PPID");
                                result.append(" LEFT JOIN T_CUSCOM_PRODUCT PRO ON PRO.PPID = SUP.PPID");
                                result.append(" LEFT JOIN T_ESHOP_CUSCOM_SUB SUB");
                                result.append(" ON SUP.TYPE_PPID = SUB.TYEPPPID AND SUB.SCCSID = PRO.SCCSID");
                                result.append(" WHERE PVB.BROKERAGE > 0 AND PVB.TYPEPPID IS NOT NULL");
                                result.append(" AND SUB.TYEPPPID NOT IN(?,?,?) AND PVB.VIPID=?");
                                break;
                            case 3: //历史表
                                result.append("SELECT PRO.PPID, SUB.SCCID, PVB.BROKERAGE, SUB.TYEPPPID");
                                result.append(" FROM DT_PRO_VIP_BRO_HISTORY PVB");
                                result.append(" LEFT JOIN DT_PRO_SETUP_SUB SUP ON PVB.PPID = SUP.PPID AND PVB.TYPEPPID = SUP.TYPE_PPID");
                                result.append(" LEFT JOIN T_CUSCOM_PRODUCT PRO ON PRO.PPID = SUP.PPID");
                                result.append(" LEFT JOIN T_ESHOP_CUSCOM_SUB SUB");
                                result.append(" ON SUP.TYPE_PPID = SUB.TYEPPPID AND SUB.SCCSID = PRO.SCCSID");
                                result.append(" WHERE PVB.BROKERAGE > 0 AND PVB.TYPEPPID IS NOT NULL");
                                result.append(" AND SUB.TYEPPPID NOT IN(?,?,?) AND PVB.VIPID=?");
                                break;
                        }
                        break;
                    case 4: //活动价
                        result.append("SELECT PRO.PPID, SUB.SCCID, PAB.BROKERAGE, SUB.TYEPPPID");
                        result.append(" FROM DT_PRO_ACTIVITY_BROKERAGE PAB");
                        result.append(" LEFT JOIN DT_PRO_SETUP_SUB SUP");
                        result.append(" ON PAB.PPID = SUP.PPID AND PAB.TYPEPPID = SUP.TYPE_PPID");
                        result.append(" LEFT JOIN T_CUSCOM_PRODUCT PRO ON PRO.PPID = SUP.PPID");
                        result.append(" LEFT JOIN T_ESHOP_CUSCOM_SUB SUB");
                        result.append(" ON SUP.TYPE_PPID = SUB.TYEPPPID AND SUB.SCCSID = PRO.SCCSID");
                        result.append(" WHERE PAB.BROKERAGE > 0 AND PAB.TYPEPPID IS NOT NULL");
                        result.append(" AND SUB.TYEPPPID NOT IN(?,?,?) AND PAB.ACTPRICEID = ?");
                        break;
                    case 5: //特价
                        result.append("SELECT PRO.PPID, SUB.SCCID, PAB.BROKERAGE, SUB.TYEPPPID");
                        result.append(" FROM DT_PRO_ACTIVITY_BROKERAGE PAB");
                        result.append(" LEFT JOIN DT_PRO_SETUP_SUB SUP");
                        result.append(" ON PAB.PPID = SUP.PPID AND PAB.TYPEPPID = SUP.TYPE_PPID");
                        result.append(" LEFT JOIN T_CUSCOM_PRODUCT PRO ON PRO.PPID = SUP.PPID");
                        result.append(" LEFT JOIN T_ESHOP_CUSCOM_SUB SUB");
                        result.append(" ON SUP.TYPE_PPID = SUB.TYEPPPID AND SUB.SCCSID = PRO.SCCSID");
                        result.append(" WHERE PAB.BROKERAGE > 0 AND PAB.TYPEPPID IS NOT NULL");
                        result.append(" AND SUB.TYEPPPID NOT IN(?,?,?) AND PAB.ACTPRICEID = ?");
                        break;
                }
                break;
            case 3: //已抢购省县村代理的该产品
                switch (val2) {
                    case 1: //零售价
                        switch (val3) {
                            case 1: //
                                result.append("SELECT PRO.PPID,SUB.SCCID,SUP.AMOUNT,SUB.TYEPPPID,P.GOODSNAME");
                                result.append(" FROM DT_PRO_SETUP_SUB SUP,T_CUSCOM_PRODUCT PRO,T_ESHOP_CUSCOM_SUB SUB,DT_PRODUCTPACKAGING P");
                                result.append(" WHERE SUB.SCCSID=PRO.SCCSID AND PRO.PPID=SUP.PPID AND SUB.AREAPPID=P.PPID");
                                result.append(" AND SUP.AMOUNT > 0 AND SUP.TYPE_PPID IS NOT NULL AND SUP.STATE = 01");
                                result.append(" AND SUP.TYPE_PPID=SUB.TYEPPPID AND SUB.TYEPPPID IN(?,?,?) AND PRO.PPID= ?");
                                break;
                            case 2: //主表
                                result.append(" SELECT PRO.PPID, SUB.SCCID, SUP.AMOUNT, SUB.TYEPPPID, P.GOODSNAME");
                                result.append(" FROM DT_PRO_SETUP_SUB SUP");
                                result.append(" LEFT JOIN T_CUSCOM_PRODUCT PRO ON PRO.PPID = SUP.PPID");
                                result.append(" LEFT JOIN T_ESHOP_CUSCOM_SUB SUB ON SUB.SCCSID = PRO.SCCSID AND SUP.TYPE_PPID = SUB.TYEPPPID");
                                result.append(" LEFT JOIN DT_PRODUCTPACKAGING P ON SUB.AREAPPID = P.PPID");
                                result.append(" WHERE SUP.AMOUNT > 0 AND SUP.STATE = 01 AND SUP.TYPE_PPID IS NOT NULL");
                                result.append(" AND SUB.TYEPPPID IN (?, ?, ?) AND SUP.SUID = ?");
                                break;
                            case 3: //历史表
                                result.append("SELECT PRO.PPID, SUB.SCCID, SUP.AMOUNT, SUB.TYEPPPID, P.GOODSNAME");
                                result.append(" FROM DT_PRO_SETUP_SUB_BACKUP SUP");
                                result.append(" LEFT JOIN T_CUSCOM_PRODUCT PRO ON PRO.PPID = SUP.PPID");
                                result.append(" LEFT JOIN T_ESHOP_CUSCOM_SUB SUB ON SUB.SCCSID = PRO.SCCSID AND SUP.TYPE_PPID = SUB.TYEPPPID");
                                result.append(" LEFT JOIN DT_PRODUCTPACKAGING P ON SUB.AREAPPID = P.PPID");
                                result.append(" WHERE SUP.AMOUNT > 0 AND SUP.STATE = 01 AND SUP.TYPE_PPID IS NOT NULL");
                                result.append(" AND SUB.TYEPPPID IN (?, ?, ?) AND SUP.SUBID = ?");
                                break;
                        }
                        break;
                    case 2: //批发价
                        switch (val3) {
                            case 2: //主表
                                result.append("SELECT PRO.PPID, SUB.SCCID, WHO.BROKERAGE, SUB.TYEPPPID, P.GOODSNAME");
                                result.append(" FROM DT_PRO_WHO_BROKERAGE WHO");
                                result.append(" LEFT JOIN DT_PRO_SETUP_SUB SUP ON WHO.PPID = SUP.PPID AND WHO.TYPEPPID = SUP.TYPE_PPID");
                                result.append(" LEFT JOIN T_CUSCOM_PRODUCT PRO ON PRO.PPID = SUP.PPID");
                                result.append(" LEFT JOIN T_ESHOP_CUSCOM_SUB SUB ON SUP.TYPE_PPID = SUB.TYEPPPID AND SUB.SCCSID = PRO.SCCSID");
                                result.append(" LEFT JOIN DT_PRODUCTPACKAGING P ON SUB.AREAPPID = P.PPID");
                                result.append(" WHERE WHO.BROKERAGE > 0 AND WHO.TYPEPPID IS NOT NULL");
                                result.append(" AND SUP.STATE = 01 AND SUB.TYEPPPID IN (?, ?, ?) AND WHO.WHOLESALEID = ?");
                                break;
                            case 3: //历史表
                                result.append("SELECT PRO.PPID, SUB.SCCID, WHO.BROKERAGE, SUB.TYEPPPID, P.GOODSNAME");
                                result.append(" FROM DT_PRO_WHO_BRO_HISTORY WHO");
                                result.append(" LEFT JOIN DT_PRO_SETUP_SUB SUP ON WHO.PPID = SUP.PPID AND WHO.TYPEPPID = SUP.TYPE_PPID");
                                result.append(" LEFT JOIN T_CUSCOM_PRODUCT PRO ON PRO.PPID = SUP.PPID");
                                result.append(" LEFT JOIN T_ESHOP_CUSCOM_SUB SUB ON SUP.TYPE_PPID = SUB.TYEPPPID AND SUB.SCCSID = PRO.SCCSID");
                                result.append(" LEFT JOIN DT_PRODUCTPACKAGING P ON SUB.AREAPPID = P.PPID");
                                result.append(" WHERE WHO.BROKERAGE > 0 AND WHO.TYPEPPID IS NOT NULL");
                                result.append(" AND SUP.STATE = 01 AND SUB.TYEPPPID IN (?, ?, ?) AND WHO.WHOLESALEID = ?");
                                break;
                        }
                        break;
                    case 3: //vip价
                        switch (val3) {
                            case 2: //主表
                                result.append("SELECT PRO.PPID, SUB.SCCID, PVB.BROKERAGE, SUB.TYEPPPID, P.GOODSNAME");
                                result.append(" FROM DT_PRO_VIP_BROKERAGE PVB");
                                result.append(" LEFT JOIN DT_PRO_SETUP_SUB SUP ON PVB.PPID = SUP.PPID AND PVB.TYPEPPID = SUP.TYPE_PPID");
                                result.append(" LEFT JOIN T_CUSCOM_PRODUCT PRO ON PRO.PPID = SUP.PPID");
                                result.append(" LEFT JOIN T_ESHOP_CUSCOM_SUB SUB ON SUP.TYPE_PPID = SUB.TYEPPPID AND SUB.SCCSID = PRO.SCCSID");
                                result.append(" LEFT JOIN DT_PRODUCTPACKAGING P ON SUB.AREAPPID = P.PPID");
                                result.append(" WHERE PVB.BROKERAGE > 0 AND PVB.TYPEPPID IS NOT NULL AND SUP.STATE = 01");
                                result.append(" AND SUB.TYEPPPID IN(?,?,?) AND PVB.VIPID = ?");
                                break;
                            case 3: //历史表
                                result.append("SELECT PRO.PPID, SUB.SCCID, PVB.BROKERAGE, SUB.TYEPPPID, P.GOODSNAME");
                                result.append(" FROM DT_PRO_VIP_BRO_HISTORY PVB");
                                result.append(" LEFT JOIN DT_PRO_SETUP_SUB SUP ON PVB.PPID = SUP.PPID AND PVB.TYPEPPID = SUP.TYPE_PPID");
                                result.append(" LEFT JOIN T_CUSCOM_PRODUCT PRO ON PRO.PPID = SUP.PPID");
                                result.append(" LEFT JOIN T_ESHOP_CUSCOM_SUB SUB ON SUP.TYPE_PPID = SUB.TYEPPPID AND SUB.SCCSID = PRO.SCCSID");
                                result.append(" LEFT JOIN DT_PRODUCTPACKAGING P ON SUB.AREAPPID = P.PPID");
                                result.append(" WHERE PVB.BROKERAGE > 0 AND PVB.TYPEPPID IS NOT NULL AND SUP.STATE = 01");
                                result.append(" AND SUB.TYEPPPID IN(?,?,?) AND PVB.VIPID = ?");
                                break;
                        }
                        break;
                    case 4: //活动价
                        result.append("SELECT PRO.PPID, SUB.SCCID, PAB.BROKERAGE, SUB.TYEPPPID, P.GOODSNAME");
                        result.append(" FROM DT_PRO_ACTIVITY_BROKERAGE PAB");
                        result.append(" LEFT JOIN DT_PRO_SETUP_SUB SUP");
                        result.append(" ON PAB.PPID = SUP.PPID AND PAB.TYPEPPID = SUP.TYPE_PPID");
                        result.append(" LEFT JOIN T_CUSCOM_PRODUCT PRO ON PRO.PPID = SUP.PPID");
                        result.append(" LEFT JOIN T_ESHOP_CUSCOM_SUB SUB");
                        result.append(" ON SUP.TYPE_PPID = SUB.TYEPPPID AND SUB.SCCSID = PRO.SCCSID");
                        result.append(" LEFT JOIN DT_PRODUCTPACKAGING P ON SUB.AREAPPID = P.PPID");
                        result.append(" WHERE PAB.BROKERAGE > 0 AND PAB.TYPEPPID IS NOT NULL AND SUP.STATE = 01");
                        result.append(" AND SUB.TYEPPPID IN(?,?,?) AND PAB.ACTPRICEID = ?");
                        break;
                    case 5: //特价
                        result.append("SELECT PRO.PPID, SUB.SCCID, PAB.BROKERAGE, SUB.TYEPPPID, P.GOODSNAME");
                        result.append(" FROM DT_PRO_ACTIVITY_BROKERAGE PAB");
                        result.append(" LEFT JOIN DT_PRO_SETUP_SUB SUP");
                        result.append(" ON PAB.PPID = SUP.PPID AND PAB.TYPEPPID = SUP.TYPE_PPID");
                        result.append(" LEFT JOIN T_CUSCOM_PRODUCT PRO ON PRO.PPID = SUP.PPID");
                        result.append(" LEFT JOIN T_ESHOP_CUSCOM_SUB SUB");
                        result.append(" ON SUP.TYPE_PPID = SUB.TYEPPPID AND SUB.SCCSID = PRO.SCCSID");
                        result.append(" LEFT JOIN DT_PRODUCTPACKAGING P ON SUB.AREAPPID = P.PPID");
                        result.append(" WHERE PAB.BROKERAGE > 0 AND PAB.TYPEPPID IS NOT NULL AND SUP.STATE = 01");
                        result.append(" AND SUB.TYEPPPID IN(?,?,?) AND PAB.ACTPRICEID = ?");
                        break;
                }
                break;
            case 4: //客户积分
                switch (val2) {
                    case 1: //零售价
                        switch (val3) {
                            case 1: //
                                result.append("SELECT PSS.AMOUNT FROM DT_PRODUCTPACKAGING P,DT_PRO_SETUP S,DT_PRO_SETUP_SUB PSS");
                                result.append(" WHERE P.PPID = S.PPID AND S.SUID = PSS.SUID AND PSS.TYPE_PPID = ? AND P.PPID = ?");
                                break;
                            case 2: //主表
                                result.append("SELECT PSS.AMOUNT FROM DT_PRO_SETUP_SUB PSS WHERE PSS.TYPE_PPID = ? AND PSS.SUID=?");
                                break;
                            case 3: //历史表
                                result.append("SELECT PSS.AMOUNT FROM DT_PRO_SETUP_SUB_BACKUP PSS WHERE PSS.TYPE_PPID = ? AND PSS.SUBID=?");
                                break;
                        }
                        break;
                    case 2: //批发价
                        switch (val3) {
                            case 2: //主表
                                result.append("SELECT WHO.BROKERAGE FROM DT_PRO_WHO_BROKERAGE WHO WHERE  WHO.TYPEPPID = ? AND WHO.WHOLESALEID = ?");
                                break;
                            case 3: //历史表
                                result.append("SELECT WHO.BROKERAGE FROM DT_PRO_WHO_BRO_HISTORY WHO WHERE  WHO.TYPEPPID = ? AND WHO.WHOLESALEID = ?");
                                break;
                        }
                        break;
                    case 3: //vip价
                        switch (val3) {
                            case 2: //主表
                                result.append("SELECT PVB.BROKERAGE FROM DT_PRO_VIP_BROKERAGE PVB WHERE PVB.TYPEPPID = ? AND PVB.VIPID=?");
                                break;
                            case 3: //历史表
                                result.append("SELECT PVB.BROKERAGE FROM DT_PRO_VIP_BRO_HISTORY PVB WHERE PVB.TYPEPPID = ? AND PVB.VIPID=?");
                                break;
                        }
                        break;
                    case 4: //活动价
                        result.append("SELECT PAB.BROKERAGE FROM DT_PRO_ACTIVITY_BROKERAGE PAB WHERE PAB.TYPEPPID = ? AND PAB.ACTPRICEID = ?");
                        break;
                    case 5: //活动价
                        result.append("SELECT PAB.BROKERAGE FROM DT_PRO_ACTIVITY_BROKERAGE PAB WHERE PAB.TYPEPPID = ? AND PAB.ACTPRICEID = ?");
                        break;
                }
                break;
            case 5: //设备投资
                switch (val2) {
                    case 1: //零售价
                        switch (val3) {
                            case 1: //
                                result.append("SELECT PSS.AMOUNT,S.TZ_TYPE FROM DT_PRO_SETUP S, DT_PRO_SETUP_SUB PSS");
                                result.append(" WHERE S.SUID = PSS.SUID AND PSS.TYPE_PPID = ? AND S.PPID = ?");
                                break;
                            case 2: //主表
                                result.append("SELECT PSS.AMOUNT, S.TZ_TYPE FROM DT_PRO_SETUP S");
                                result.append(" LEFT JOIN DT_PRO_SETUP_SUB PSS ON S.SUID = PSS.SUID");
                                result.append(" WHERE PSS.TYPE_PPID = ? AND S.SUID=?");
                                break;
                            case 3: //历史表
                                result.append("SELECT PSS.AMOUNT, S.TZ_TYPE FROM DT_PRO_SETUP_BACKUP S");
                                result.append(" LEFT JOIN DT_PRO_SETUP_SUB_BACKUP PSS ON S.SUBID = PSS.SUBID");
                                result.append(" WHERE PSS.TYPE_PPID = ? AND S.SUBID=?");
                                break;
                        }
                        break;
                    case 2: //批发价
                        switch (val3) {
                            case 2: //主表
                                result.append("SELECT WHO.BROKERAGE, W.INVESTTYPE FROM DT_PRO_WHOLESALE W");
                                result.append(" LEFT JOIN DT_PRO_WHO_BROKERAGE WHO ON W.WHOLESALEID = WHO.WHOLESALEID");
                                result.append(" WHERE  WHO.TYPEPPID = ? AND W.WHOLESALEID=?");
                                break;
                            case 3: //历史表
                                result.append("SELECT WHO.BROKERAGE, W.INVESTTYPE FROM DT_PRO_WHO_HISTORY W");
                                result.append(" LEFT JOIN DT_PRO_WHO_BRO_HISTORY WHO ON W.WHOLESALEID = WHO.WHOLESALEID");
                                result.append(" WHERE WHO.TYPEPPID = ? AND W.WHOLESALEID=?");
                                break;
                        }
                        break;
                    case 3: //vip价
                        switch (val3) {
                            case 2: //主表
                                result.append("SELECT PVB.BROKERAGE, V.INVESTTYPE FROM DT_PRO_VIP V");
                                result.append(" LEFT JOIN DT_PRO_VIP_BROKERAGE PVB ON V.VIPID = PVB.VIPID");
                                result.append(" WHERE PVB.TYPEPPID = ? AND V.VIPID=?");
                                break;
                            case 3: //历史表
                                result.append("SELECT PVB.BROKERAGE, V.INVESTTYPE FROM DT_PRO_VIP_HISTORY V");
                                result.append(" LEFT JOIN DT_PRO_VIP_BRO_HISTORY PVB ON V.VIPID = PVB.VIPID");
                                result.append(" WHERE PVB.TYPEPPID = ? AND V.VIPID=?");
                                break;
                        }
                        break;
                    case 4: //活动价
                        result.append("SELECT PAB.BROKERAGE, PAP.INVESTTYPE FROM DT_PRO_ACTIVITY_PRICE PAP");
                        result.append(" LEFT JOIN DT_PRO_ACTIVITY_BROKERAGE PAB ON PAB.ACTPRICEID = PAP.ACTPRICEID");
                        result.append(" WHERE PAB.TYPEPPID = ? AND PAP.ACTPRICEID = ?");
                        break;
                    case 5: //活动价
                        result.append("SELECT PAB.BROKERAGE, PAP.INVESTTYPE FROM DT_PRO_ACTIVITY_PRICE PAP");
                        result.append(" LEFT JOIN DT_PRO_ACTIVITY_BROKERAGE PAB ON PAB.ACTPRICEID = PAP.ACTPRICEID");
                        result.append(" WHERE PAB.TYPEPPID = ? AND PAP.ACTPRICEID = ?");
                        break;
                }
                break;
            case 6: //查询没分出去的佣金总数
                switch (val2) {
                    case 1: //零售价
                        switch (val3) {
                            case 1: //
                                result.append("SELECT SUM(PSS.AMOUNT) FROM DT_PRO_SETUP S,DT_PRO_SETUP_SUB PSS WHERE (PSS.STATE=? OR PSS.STATE IS NULL)");
                                result.append(" AND S.SUID = PSS.SUID AND PSS.TYPE_PPID <> ? AND PSS.TYPE_PPID <> ? AND S.PPID = ?");
                                break;
                            case 2: //主表
                                result.append("SELECT SUM(PSS.AMOUNT) FROM DT_PRO_SETUP_SUB PSS");
                                result.append(" WHERE (PSS.STATE = ? OR PSS.STATE IS NULL)");
                                result.append(" AND PSS.TYPE_PPID <> ? AND PSS.TYPE_PPID <> ? AND PSS.SUID = ?");
                                break;
                            case 3: //历史表
                                result.append("SELECT SUM(PSS.AMOUNT) FROM DT_PRO_SETUP_SUB_BACKUP PSS");
                                result.append(" WHERE (PSS.STATE = ? OR PSS.STATE IS NULL)");
                                result.append(" AND PSS.TYPE_PPID <> ? AND PSS.TYPE_PPID <> ? AND PSS.SUBID = ?");
                                break;
                        }
                        break;
                    case 2: //批发价
                        switch (val3) {
                            case 2: //主表
                                result.append("SELECT SUM(WHO.BROKERAGE) FROM DT_PRO_WHO_BROKERAGE WHO");
                                result.append(" LEFT JOIN DT_PRO_SETUP_SUB SUP ON WHO.PPID = SUP.PPID AND WHO.TYPEPPID = SUP.TYPE_PPID");
                                result.append(" WHERE (SUP.STATE = ? OR SUP.STATE IS NULL) AND WHO.TYPEPPID <> ?");
                                result.append(" AND WHO.TYPEPPID <> ? AND WHO.WHOLESALEID=?");
                                break;
                            case 3: //历史表
                                result.append("SELECT SUM(WHO.BROKERAGE) FROM DT_PRO_WHO_BRO_HISTORY WHO");
                                result.append(" LEFT JOIN DT_PRO_SETUP_SUB SUP ON WHO.PPID = SUP.PPID AND WHO.TYPEPPID = SUP.TYPE_PPID");
                                result.append(" WHERE (SUP.STATE = ? OR SUP.STATE IS NULL) AND WHO.TYPEPPID <> ?");
                                result.append(" AND WHO.TYPEPPID <> ? AND WHO.WHOLESALEID=?");
                                break;
                        }
                        break;
                    case 3: //vip价
                        switch (val3) {
                            case 2: //主表
                                result.append("SELECT SUM(PVB.BROKERAGE) FROM DT_PRO_VIP_BROKERAGE PVB");
                                result.append(" LEFT JOIN DT_PRO_SETUP_SUB SUP ON PVB.PPID = SUP.PPID AND PVB.TYPEPPID = SUP.TYPE_PPID");
                                result.append(" WHERE (SUP.STATE = ? OR SUP.STATE IS NULL) AND PVB.TYPEPPID <> ?");
                                result.append(" AND PVB.TYPEPPID <> ? AND PVB.VIPID=?");
                                break;
                            case 3: //历史表
                                result.append("SELECT SUM(PVB.BROKERAGE) FROM DT_PRO_VIP_BRO_HISTORY PVB");
                                result.append(" LEFT JOIN DT_PRO_SETUP_SUB SUP ON PVB.PPID = SUP.PPID AND PVB.TYPEPPID = SUP.TYPE_PPID");
                                result.append(" WHERE (SUP.STATE = ? OR SUP.STATE IS NULL) AND PVB.TYPEPPID <> ?");
                                result.append(" AND PVB.TYPEPPID <> ? AND PVB.VIPID=?");
                                break;
                        }
                        break;
                    case 4: //活动价
                        result.append("SELECT NVL(SUM(PAB.BROKERAGE), 0) FROM DT_PRO_ACTIVITY_BROKERAGE PAB");
                        result.append(" LEFT JOIN DT_PRO_SETUP_SUB SUP ON PAB.PPID = SUP.PPID AND PAB.TYPEPPID = SUP.TYPE_PPID");
                        result.append(" WHERE (SUP.STATE = ? OR SUP.STATE IS NULL) AND PAB.TYPEPPID <> ?");
                        result.append(" AND PAB.TYPEPPID <> ? AND PAB.ACTPRICEID = ?");
                        break;
                    case 5: //活动价
                        result.append("SELECT NVL(SUM(PAB.BROKERAGE), 0) FROM DT_PRO_ACTIVITY_BROKERAGE PAB");
                        result.append(" LEFT JOIN DT_PRO_SETUP_SUB SUP ON PAB.PPID = SUP.PPID AND PAB.TYPEPPID = SUP.TYPE_PPID");
                        result.append(" WHERE (SUP.STATE = ? OR SUP.STATE IS NULL) AND PAB.TYPEPPID <> ?");
                        result.append(" AND PAB.TYPEPPID <> ? AND PAB.ACTPRICEID = ?");
                        break;
                }
                break;
        }
        return result;
    }


    /**
     * 产品详情显示价格
     *
     * @param val1
     * @param val2
     * @return
     */
    public static StringBuilder getShowPriceSql(int val1, int val2) {
        StringBuilder result = new StringBuilder();
        switch (val1) {
            case 1: //产品详情
                switch (val2) {
                    //成本，卖价，佣金
                    case 1: //零售价

                        result.append("select p.goodsname,ps.ppid,ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2) pri,p.image,p.goodsid,p.companyid,p.type,p.monthSales");
                        result.append(" from DT_PRO_SETUP ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
                        result.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append("  where ps.com_id = ? and p.ppid = ?");

                        break;
                    case 2: //批发价
                        result.append("select p.goodsname,ps.ppid,ROUND(ps.wholesale*(1+nvl(sz.total_pct,0)/100),2) pri,p.image,p.goodsid,p.companyid,p.type,p.monthSales");
                        result.append(" from dt_pro_wholesale ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
                        result.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append(" where ps.companyId = ? and p.ppid = ?");

                        break;
                    case 3: //vip价
                        result.append("select p.goodsname,ps.ppid,ROUND(ps.vip*(1+nvl(sz.total_pct,0)/100),2) pri,p.image,p.goodsid,p.companyid,p.type,p.monthSales");
                        result.append(" from dt_pro_vip ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
                        result.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append(" where ps.companyId = ? and p.ppid = ? ");
                        result.append(" and ps.state='00'");
                        break;
                    case 4: //促销价
                        result.append("select p.goodsname,ps.ppid,ROUND(ps.actPrice*(1+nvl(sz.total_pct,0)/100),2) pri,p.image,p.goodsid,p.companyid,p.type,p.monthSales");
                        result.append(" from dt_pro_activity_price ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
                        result.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append(" inner join dt_pro_activity pa on pa.activityId = ps.activityid ");
                        result.append(" where pa.type ='00' and ps.companyId = ? and p.ppid = ? ");
                        result.append(" and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
                        result.append(" and pa.state ='01' and ps.state ='00'");
                        break;
                    case 5: //特价
                        result.append("select p.goodsname,ps.ppid,ROUND(ps.actPrice*(1+nvl(sz.total_pct,0)/100),2) pri,p.image,p.goodsid,p.companyid,p.type,p.monthSales");
                        result.append(" from dt_pro_activity_price ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
                        result.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append(" inner join dt_pro_activity pa on pa.activityId = ps.activityid ");
                        result.append(" where pa.type ='01' and ps.companyId = ? and p.ppid = ? ");
                        result.append(" and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
                        result.append(" and pa.state ='01' and ps.state ='00'");
                        break;

                }
                break;
            case 2: //确认订单获取价格
                switch (val2) {
                    case 1: //零售价
                        result.append(" select ROUND(p.re_price*(1+nvl(sz.total_pct,0)/100),2) pri,p.SUID from DT_PRO_SETUP p");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.COM_ID ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
                        result.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append(" where ppid = ?");
                        break;
                    case 2: //批发价
                        result.append(" select ROUND(p.wholesale*(1+nvl(sz.total_pct,0)/100),2) pri,p.WHOLESALEID from dt_pro_wholesale p");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
                        result.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01' ");
                        result.append(" where p.ppid = ?");

                        break;
                    case 3: //vip价
                        result.append(" select ROUND(p.vip*(1+nvl(sz.total_pct,0)/100),2) pri,p.VIPID from dt_pro_vip p");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
                        result.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append("  where p.ppid = ? ");
                        result.append("  and p.state='00'");
                        break;
                    case 4: //促销价
                        result.append(" select ROUND(p.actPrice*(1+nvl(sz.total_pct,0)/100),2) pri ,p.actPriceId from dt_pro_activity_price p");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
                        result.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append(" inner join dt_pro_activity pa on pa.activityId = p.activityid ");
                        result.append(" where pa.type ='00' and p.ppid = ? ");
                        result.append(" and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
                        result.append(" and pa.state ='01' and p.state ='00'");
                        break;
                    case 5: //特价
                        result.append(" select ROUND(p.actPrice*(1+nvl(sz.total_pct,0)/100),2) pri,p.actPriceId from dt_pro_activity_price p");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
                        result.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append(" inner join dt_pro_activity pa on pa.activityId = p.activityid ");
                        result.append(" where pa.type ='01' and p.ppid = ? ");
                        result.append(" and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
                        result.append(" and pa.state ='01' and p.state ='00'");
                        break;

                }
                break;
            case 3: //单产品下订单
                switch (val2) {
                    case 1: //零售价的成本价
                        result.append("select S.EF_PRICE from DT_PRO_SETUP S where S.PPID = ? AND S.COM_ID = ? AND 1=?");
                        break;

                    case 2: //批发价的成本价
                        result.append("select S.factory from dt_pro_wholesale S where S.PPID = ? AND S.companyId = ? AND 1=?");
                        break;

                    case 3: //vip价的成本价
                        result.append("select S.factory from dt_pro_vip S where S.PPID = ? AND S.COMPANYID = ? AND 1=? AND S.STATE ='00'");
                        break;

                    /*case 4: //促销价[普通活动]的成本价
                        result.append("select p.factory from dt_pro_activity_price p ");
                        result.append(" left join dt_pro_activity pa on pa.activityId = p.activityid ");
                        result.append(" where pa.type ='00' and p.ppid = ?  AND pa.COMPANYID = ?");
                        result.append(" and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
                        result.append(" and pa.state ='01'");
                        break;
                    case 5: //特价[特价活动]的成本价
                        result.append("select p.factory from dt_pro_activity_price p ");
                        result.append(" left join dt_pro_activity pa on pa.activityId = p.activityid ");
                        result.append(" where pa.type ='01' and p.ppid = ? AND S.COM_ID = ?");
                        result.append(" and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
                        result.append(" and pa.state ='01'");
                        break;*/
                    case 4: //活动价
                        result.append("SELECT PAP.FACTORY FROM DT_PRO_ACTIVITY_PRICE PAP");
                        result.append(" WHERE PAP.PPID = ? AND PAP.COMPANYID = ? AND PAP.ACTPRICEID = ?");
                        result.append(" AND PAP.STATE ='00'");
                        break;
                    case 5: //特价[特价活动]的成本价
                        result.append("SELECT PAP.FACTORY FROM DT_PRO_ACTIVITY_PRICE PAP");
                        result.append(" WHERE PAP.PPID = ? AND PAP.COMPANYID = ? AND PAP.ACTPRICEID = ?");
                        result.append(" AND PAP.STATE ='00'");
                        break;
                }
                break;
            case 4: //获取购物车
                switch (val2) {
                    case 1: //零售价
                        result.append("select ps.ppid,p.goodsname,p.image,p.type, ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2) pri,p.companyid,cm.companyName,c.itemNum,c.stardard,c.cart_Id");
                        result.append(" from DT_PRO_SETUP ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
                        result.append(" inner join DT_cart c on p.ppid = c.pid ");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append("  where ps.fcom_id is null and c.staff_id = ? and p.showweixin = ?");

                        break;
                    case 2: //批发价
                        result.append("select ps.ppid,p.goodsname,p.image,p.type, ROUND(ps.wholesale*(1+nvl(sz.total_pct,0)/100),2) pri,p.companyid,cm.companyName,c.itemNum,c.stardard,c.cart_Id");
                        result.append(" from DT_PRO_WHOLESALE ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
                        result.append(" inner join DT_cart c on p.ppid = c.pid ");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append(" where c.staff_id = ? and p.showweixin = ?");
                        break;
                    case 3: //vip价
                        result.append("select ps.ppid,p.goodsname,p.image,p.type, ROUND(ps.vip*(1+nvl(sz.total_pct,0)/100),2) pri,p.companyid,cm.companyName,c.itemNum,c.stardard,c.cart_Id");
                        result.append(" from DT_PRO_VIP ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
                        result.append(" inner join DT_cart c on p.ppid = c.pid ");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append(" where c.staff_id = ? and p.showweixin = ? ");
                        result.append(" and ps.state='00'");
                        break;
                    case 4: //促销价[普通活动]
                        result.append("select ps.ppid,p.goodsname,p.image,p.type, ROUND(ps.actPrice*(1+nvl(sz.total_pct,0)/100),2) pri,p.companyid,cm.companyName,c.itemNum,c.stardard,c.cart_Id");
                        result.append(" from dt_pro_activity_price ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
                        result.append(" inner join DT_cart c on p.ppid = c.pid ");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append(" inner join dt_pro_activity pa on pa.activityId = ps.activityid");
                        result.append(" where pa.type ='00' and c.staff_id = ? and p.showweixin = ? ");
                        result.append(" and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
                        result.append(" and pa.state ='01' and ps.state ='00'");
                        break;
                    case 5: //特价[特价活动]
                        result.append("select ps.ppid,p.goodsname,p.image,p.type, ROUND(ps.actPrice*(1+nvl(sz.total_pct,0)/100),2) pri,p.companyid,cm.companyName,c.itemNum,c.stardard,c.cart_Id");
                        result.append(" from dt_pro_activity_price ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
                        result.append(" inner join DT_cart c on p.ppid = c.pid ");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append(" inner join dt_pro_activity pa on pa.activityId = ps.activityid");
                        result.append(" where pa.type ='01' and c.staff_id = ? and p.showweixin = ? ");
                        result.append(" and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
                        result.append(" and pa.state ='01' and ps.state ='00'");
                        break;
                }
                break;
            case 5: //购物车确认订单
                switch (val2) {
                    case 1: //零售价

                        result.append("select ps.ppid,p.goodsname,p.image,p.type,ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2) pri,p.companyid,c.itemNum,c.stardard,c.cart_Id");
                        result.append(" from DT_PRO_SETUP ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
                        result.append(" inner join DT_cart c on p.ppid = c.pid ");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append("  where ps.fcom_id is null and ps.ppid = ? and c.staff_id = ? and c.pos = ? and c.pricetype = ?");

                        break;
                    case 2: //批发价
                        result.append("select ps.ppid,p.goodsname,p.image,p.type,ROUND(ps.wholesale*(1+nvl(sz.total_pct,0)/100),2) pri,p.companyid,c.itemNum,c.stardard,c.cart_Id");
                        result.append(" from DT_PRO_WHOLESALE ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
                        result.append(" inner join DT_cart c on p.ppid = c.pid ");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append("  where ps.ppid = ? and c.staff_id = ? and c.pos = ? and c.pricetype = ?");
                        break;
                    case 3: //vip价
                        result.append("select ps.ppid,p.goodsname,p.image,p.type,ROUND(ps.vip*(1+nvl(sz.total_pct,0)/100),2) pri,p.companyid,c.itemNum,c.stardard,c.cart_Id");
                        result.append(" from DT_PRO_VIP ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
                        result.append(" inner join DT_cart c on p.ppid = c.pid ");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append("  where ps.ppid = ? and c.staff_id = ? and c.pos = ? and c.pricetype = ? ");
                        result.append("  and ps.state='00'");
                        break;
                    case 4: //促销价[普通活动]
                        result.append("select ps.ppid,p.goodsname,p.image,p.type,ROUND(ps.actPrice*(1+nvl(sz.total_pct,0)/100),2) pri,p.companyid,c.itemNum,c.stardard,c.cart_Id");
                        result.append(" from dt_pro_activity_price ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
                        result.append(" inner join DT_cart c on p.ppid = c.pid ");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append(" inner join dt_pro_activity pa on pa.activityId = ps.activityid ");
                        result.append(" where pa.type ='00' and ps.ppid = ? and c.staff_id = ? and c.pos = ? and c.pricetype = ? ");
                        result.append(" and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
                        result.append(" and pa.state ='01' and ps.state ='00'");
                        break;
                    case 5: //特价[特价活动]
                        result.append("select ps.ppid,p.goodsname,p.image,p.type,ROUND(ps.actPrice*(1+nvl(sz.total_pct,0)/100),2) pri,p.companyid,c.itemNum,c.stardard,c.cart_Id");
                        result.append(" from dt_pro_activity_price ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
                        result.append(" inner join DT_cart c on p.ppid = c.pid ");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append(" inner join dt_pro_activity pa on pa.activityId = ps.activityid ");
                        result.append(" where pa.type ='01' and ps.ppid = ? and c.staff_id = ? and c.pos = ? and c.pricetype = ? ");
                        result.append(" and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
                        result.append(" and pa.state ='01' and ps.state ='00'");
                        break;
                }
                break;
            case 6: //购物车提交订单
                switch (val2) {
                    case 1: //零售价
                        result.append("select ps.ppid,p.goodsname,p.goodsid,p.type,ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2) pri,p.companyid,p.productCode,p.model,ps.ef_price");
                        result.append(" from DT_PRO_SETUP ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append("  where ps.com_id = ? and p.ppid = ?");
                        break;
                    case 2: //批发价
                        result.append("select ps.ppid,p.goodsname,p.goodsid,p.type,ROUND(ps.wholesale*(1+nvl(sz.total_pct,0)/100),2) pri,p.companyid,p.productCode,p.model,ps.factory");
                        result.append(" from DT_PRO_WHOLESALE ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append("  where ps.companyId = ? and p.ppid = ?");
                        break;
                    case 3: //vip价
                        result.append("select ps.ppid,p.goodsname,p.goodsid,p.type,ROUND(ps.vip*(1+nvl(sz.total_pct,0)/100),2) pri,p.companyid,p.productCode,p.model,ps.factory");
                        result.append(" from DT_PRO_VIP ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append("  where ps.companyId = ? and p.ppid = ? ");
                        result.append("  and ps.state='00' ");
                        break;
                    case 4: //促销价[普通活动]
                        result.append("select ps.ppid,p.goodsname,p.goodsid,p.type,ROUND(ps.actPrice*(1+nvl(sz.total_pct,0)/100),2) pri,p.companyid,p.productCode,p.model,ps.factory");
                        result.append(" from dt_pro_activity_price ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append(" inner join dt_pro_activity pa on pa.activityId = ps.activityid ");
                        result.append(" where pa.type ='00' and ps.companyId = ? and p.ppid = ? ");
                        result.append(" and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
                        result.append(" and pa.state ='01' and ps.state ='00' ");
                        break;
                    case 5: //特价[特价活动]
                        result.append("select ps.ppid,p.goodsname,p.goodsid,p.type,ROUND(ps.actPrice*(1+nvl(sz.total_pct,0)/100),2) pri,p.companyid,p.productCode,p.model,ps.factory");
                        result.append(" from dt_pro_activity_price ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append(" inner join dt_pro_activity pa on pa.activityId = ps.activityid ");
                        result.append(" where pa.type ='01' and ps.companyId = ? and p.ppid = ? ");
                        result.append(" and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
                        result.append(" and pa.state ='01' and ps.state ='00'");
                        break;
                }
                break;
            case 7: //变价
                switch (val2) {
                    case 1: //零售价
                        result.append("select p.ppid,p.image,p.goodsname,p.barcode,p.type,p.standard,p.brand,ps.re_price sj,");
                        result.append("ps.ef_price cb,ps.brokerage yj,ps.proxy_sum_price dl,1+nvl(sz.total_pct,0)/100 pri,ps.suid");
                        result.append(" from DT_PRO_SETUP ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
                        result.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append(" where ps.com_id = ? and ps.suid = ?");
                        break;
                    case 2: //批发价
                        result.append("select p.ppid,p.image,p.goodsname,p.barcode,p.type,p.standard,p.brand,ps.wholesale sj,");
                        result.append("ps.factory cb,ps.brokerage yj,ps.brokerages dl,1+nvl(sz.total_pct,0)/100 pri,ps.WHOLESALEID");
                        result.append(" from DT_PRO_WHOLESALE ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
                        result.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append("  where ps.companyId = ? and ps.WHOLESALEID = ?");
                        break;
                    case 3: //vip价
                        result.append("select p.ppid,p.image,p.goodsname,p.barcode,p.type,p.standard,p.brand,ps.vip sj,");
                        result.append("ps.factory cb,ps.brokerage yj,ps.brokerages dl,1+nvl(sz.total_pct,0)/100 pri,ps.VIPID ");
                        result.append(" from DT_PRO_VIP ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
                        result.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append(" where ps.companyId = ? and ps.VIPID = ? ");
                        result.append(" and ps.state='00' ");
                        break;
                    case 4: //促销价[普通活动]
                        result.append("select p.ppid,p.image,p.goodsname,p.barcode,p.type,p.standard,p.brand,ps.actPrice sj,");
                        result.append("ps.factory cb,ps.brokerage yj,ps.brokerages dl,1+nvl(sz.total_pct,0)/100 pri,p.ACTPRICEID");
                        result.append(" from dt_pro_activity_price ps left join dt_ProductPackaging p on ps.ppid = p.ppid ");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
                        result.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append(" where ps.companyId = ? and p.ACTPRICEID=? ");
                        break;
                    case 6: //变价记录
                        result.append("select p.ppid,p.image,p.goodsname,p.barcode,p.type,p.standard,p.brand,pc.re_price sj,");
                        result.append("pc.ef_price cb,pc.brokerage yj,pc.proxy_sum_price dl,1+nvl(sz.total_pct,0)/100 pri,pc.pcid");
                        result.append(" from dt_pricechange pc left join dt_ProductPackaging p on pc.ppid = p.ppid ");
                        result.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
                        result.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
                        result.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
                        result.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
                        result.append(" where pc.com_id = ? and pc.pcid = ?");
                        break;
                }
                break;
        }
        return result;
    }
}
