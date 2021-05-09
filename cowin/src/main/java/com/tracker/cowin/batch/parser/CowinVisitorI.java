package com.tracker.cowin.batch.parser;

import com.tracker.cowin.batch.dataobjects.CenterWrapper;

public interface CowinVisitorI {
	CenterWrapper visit(JobElement jobElement);
}
