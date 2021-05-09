## **Vaccine Tracker POC App**

- This is a POC Spring based application that provides the following:
    - API : An API that consumes the COWIN Open API and provides you with various filter option
    - Batch Job (Under Development) : A job that would keep track of slot availability



## **API Description**

- Below portion describes all the rest api exposed by the application

## **Methods**

**ALL**

Example : http://localhost:8080/api/cowin/all?district_id=446&date=16-05-2021

Description : Provides you with all center related information

**AVAILABLE**

Example
- http://localhost:8080/api/cowin/available?district_id=446&date=16-05-2021

Request Parameters(Optional) :

- fee_type : free/paid
- age_limit : 18/45
- type : covaxin/covishield

Examples including optional request parameters:

- http://localhost:8080/api/cowin/available?district\_id=446&date=16-05-2021&fee\_type=free
- http://localhost:8080/api/cowin/available?district\_id=446&date=16-05-2021&fee\_type=free&age_limit=18
- http://localhost:8080/api/cowin/available?district\_id=446&date=16-05-2021&fee\_type=free&age_limit=45&type=COVAXIN