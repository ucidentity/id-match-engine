package edu.berkeley.ucic.idmatch

class PendingReconcilerTask {

    static constraints = {
      lastRunTimeStamp not null;
      taskId not null;
      requestJson not null;
    }

    String taskingId;
    String stuId; //is this studId
    String empId; //is this empId
    String affId; //is this affId
    String uid; //is this someother unique key
    String requestJson;
      Date lastRunTimeStamp;
}
