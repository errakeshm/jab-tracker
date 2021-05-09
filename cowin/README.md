## **Vaccine Tracker POC App**

- This is a POC Spring based application that provides the following:
    - API : An API that consumes the COWIN Open API and provides you with various filter option
    - Batch Job : Batch Application that would keep track of slot availability
    - Messaging Based solution (In Progress) : Alert the user on that mobile device

* * *

## **API Description**

**Base URL :** [http://localhost:8080/api/cowin](http://localhost:8080/api/cowin)

## **Methods**

### ALL

Example : [http://localhost:8080/api/cowin/all?district_id=446&date=16-05-2021](http://localhost:8080/api/cowin/all?district_id=446&date=16-05-2021)

Description : Provides you with all center related information

**AVAILABLE**

Example : [http://localhost:8080/api/cowin/available?district_id=446&date=16-05-2021](http://localhost:8080/api/cowin/available?district_id=446&date=16-05-2021)

Request Parametes(Optional) :

- fee_type : free/paid
- age_limit : 18/45
- type : covaxin/covishield

Examples including optional request parameters:

- [http://localhost:8080/api/cowin/available?district\_id=446&date=16-05-2021&fee\_type=free](http://localhost:8080/api/cowin/available?district_id=446&date=16-05-2021&fee_type=free)
- [http://localhost:8080/api/cowin/available?district\_id=446&date=16-05-2021&fee\_type=free&age_limit=18](http://localhost:8080/api/cowin/available?district_id=446&date=16-05-2021&fee_type=free&age_limit=18)
- [http://localhost:8080/api/cowin/available?district\_id=446&date=16-05-2021&fee\_type=free&age_limit=45&type=COVAXIN](http://localhost:8080/api/cowin/available?district_id=446&date=16-05-2021&fee_type=free&age_limit=45&type=COVAXIN)

* * *

## **Batch Job**

- This module is responsible for checking the slot availability in a periodic manner
- The user has complete control over what needs to be run a period manner.
- Also the user is given the option of configuring the batch job as per the desired schedule schedule.
- **For FUN** : The application also **sounds an alarm **when a slot is available. To enable it mark the property **alarm.enabled=true** in configurations.properties :)

### Output

- If slots are available based on the configured job the data is printed in the system console as mentioned below :

![Example.png](:/Example)

### Configuration

- The configuration for batch job is defined in two files : beans.xml, scheduler.properties

**scheduler.properties**

```properties
# Job Names (Comma separated job names. You can define multiple)
jobs.names=available18Free09,available18Free16

# Cron expression for the job (<jobName>.scheduler.expression=0/20 * * * * ?)
available18Free09.scheduler.expression=0/20 * * * * ?
available18Free16.scheduler.expression=0/30 * * * * ?

# Whether job is enabled or not (<jobName>.job.enabled=true|false). If job is not enabled it wont be run by the scheduler
available18Free09.job.enabled=false
available18Free16.job.enabled=true
```

**beans.xml**

```xml
<!-- Define job configuration. Name should be same as <jobName> defined in scheduler.properties -->
<bean name="available18Free16" class="com.tracker.cowin.batch.dataobjects.JobConfiguration">
    <!-- jobName --> 
        <property name="name" value="available18Free"></property>
        <property name="api" value="${calendarByDistricts}"></property>
        <property name="params" ref="available18Free16-params"></property>
    </bean>
    
    <!-- All the query parameters are defined as key-value pair.-->
    <util:map id="available18Free16-params" key-type="java.lang.String" value-type="java.lang.String">
        <entry key="district_id" value="446"></entry>
        <entry key="date" value="16-05-2021"></entry>
        <entry key="fee_type" value="free"></entry>
        <entry key="age_limit" value="45"></entry>
    </util:map>
```