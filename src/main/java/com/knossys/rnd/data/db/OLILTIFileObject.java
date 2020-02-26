package com.knossys.rnd.data.db;

/**
 * @author vvelsen
 */
public class OLILTIFileObject {
    public String fId="";
    public String fName="";
    public String fOwner="";
    public String fAssignment="";
    public String fCreated="";
    public String fCreatedRaw="";
    public String fSize="";
    public String fType="file";
    public String fCourse="";
    public String fState="";
    public String fFullname="";
    public String fOwnedBy="instructor"; // 'student' or 'instructor'
    public String fData="";

    public byte[] raw=null;


  /**
   *
   */
  public OLILTIFileObject () {
  	fId=java.util.UUID.randomUUID().toString();
  }
}
