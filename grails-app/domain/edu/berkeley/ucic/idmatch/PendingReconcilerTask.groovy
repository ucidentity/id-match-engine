package edu.berkeley.ucic.idmatch

class PendingReconcilerTask {

    static constraints = {
      lastRunTimeStamp nullable: false;
      taskId nullable: false;
      requestJson nullable: false;
    }

    String taskId;
    String stuId; //is this studId
    String empId; //is this empId
    String affId; //is this affId
    String uid; //is this someother unique key
    String requestJson;
      Date lastRunTimeStamp;
}
