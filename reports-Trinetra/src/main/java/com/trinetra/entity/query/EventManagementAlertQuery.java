package com.trinetra.entity.query;

public class EventManagementAlertQuery {
	public EventManagementAlertQuery() {

	}
	public static final String GET_ALERT_TYPE_COUNTS = "select (atm.alert_name)alertName, count(a.alert_type_id)alertCount from alert_type_master atm left join alert a on a.alert_type_id=atm.id where atm.bind_in_dropdown='Y' and a.log_date=current_date group by atm.alert_name";

	public static final String GET_DISPLAY_ON_UI_ALERT_TYPE_COUNTS = "select (atm.alert_name)alertName, count(a.alert_type_id)alertCount from alert_type_master atm left join alert a on a.alert_type_id=atm.id where atm.display_on_ui='Y' group by atm.alert_name";
	
	public static final String GET_ALERT_ANALYSIS_LIST="select (atm.alert_name)alertName, count(a.alert_type_id)alertCount from alert_type_master atm left join alert a on a.alert_type_id=atm.id where atm.alert_analysis='Y' group by atm.alert_name";
}


