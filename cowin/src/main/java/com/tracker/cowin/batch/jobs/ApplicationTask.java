package com.tracker.cowin.batch.jobs;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.tracker.cowin.batch.constants.CommonConstants;
import com.tracker.cowin.batch.dataobjects.Center;
import com.tracker.cowin.batch.dataobjects.CenterWrapper;
import com.tracker.cowin.batch.dataobjects.JobDefinition;
import com.tracker.cowin.batch.dataobjects.Session;

import de.vandermeer.asciitable.AsciiTable;

public class ApplicationTask implements Runnable{

	private JobDefinition jobDefinition;
	private JabTrackerLauncher jabTrackerLauncher;

	public ApplicationTask(JobDefinition jobDefinition, JabTrackerLauncher jabTrackerLauncher) {
		this.jobDefinition = jobDefinition;
		this.jabTrackerLauncher = jabTrackerLauncher;
	}

	@Override
	public void run() {
		try {
			CenterWrapper wrapper = this.jabTrackerLauncher.submit(jobDefinition.getName());
			if(wrapper != null) {
				List<Center> centerList = wrapper.getCenters();
				if(centerList != null && !centerList.isEmpty()) {
					
					for(Center center: centerList) {
						AsciiTable at = new AsciiTable();
						at.addRule();
						at.addRow(null,"General Information (SLOTS)");
						at.addRule();
						at.addRow("Name","Address");
						at.addRule();
						at.addRow(center.getName(), center.getAddress());
						at.addRule();
						at.addRow(null,"Sessions");
						for(Session session:center.getSessions()) {
							at.addRule();
							at.addRow("Date",session.getDate());
							at.addRule();
							at.addRow("Availability",session.getAvailableCapacity());
							at.addRule();
							at.addRow("Vaccine", session.getVaccine());
							at.addRule();
							at.addRow("Slots",session.getSlots());
						}
						at.addRule();
						System.out.println(at.render(50));
					}
					this.jabTrackerLauncher.play();
				} else {
					AsciiTable at = new AsciiTable();
					at.addRule();
					at.addRow(null,"No SLOTS found !!");
					at.addRule();
					System.out.println(at.render(50));
				}
			}
		} catch (InterruptedException | ExecutionException e) {
			CommonConstants.LOGGER.error(e.getMessage());
		} 
	}
}
