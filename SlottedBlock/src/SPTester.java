import java.io.*;
import java.util.*;

public class SPTester
{
    public static interface Testable
    {
        void test() throws Exception;
    }
    
    public static class TestFailedException extends RuntimeException
    {
        public TestFailedException(String explanation)
        {
            super(explanation);
        }
    }

    public static class Test1 implements Testable
    {
        public void test() throws Exception
        {
            SlottedBlock sp = new SlottedBlock(new Block());
            sp.init();
            
            System.out.println("--- Test 1: Block Initialization Checks ---");
            sp.setBlockId(7);
            sp.setNextBlockId(8);
            sp.setPrevBlockId(SlottedBlock.INVALID_BLOCK);
            
            System.out.println
                ("Current Block No.: " + sp.getBlockId() + ", " +
                 "Next Block Id: " + sp.getNextBlockId() + ", " +
                 "Prev Block Id: " + sp.getPrevBlockId() + ", " +
                 "Available Space: " + sp.getAvailableSpace());
        
            if (!sp.empty())
                throw new TestFailedException("Block should be empty.");

            System.out.println("Block Empty as expected.");
            RID rid = sp.firstRecord();
            sp.deleteRecord(rid);
            sp.dumpBlock();
        }
    }


    public static class Test2 implements Testable
    {
        public void test() throws Exception
        {
            int buffSize = 20; //size of record
            int limit = 35; //number of records to add
            byte[] tmpBuf = new byte[buffSize]; //construct record in byte array

            SlottedBlock sp = new SlottedBlock(new Block()); //initialize a slotted block
            sp.init();
            sp.setBlockId(7);
            sp.setNextBlockId(8);
            sp.setPrevBlockId(SlottedBlock.INVALID_BLOCK);

            System.out.println("--- Test 2: Insert and traversal of " +
                               "records ---");
            for (int i=0; i < limit; i++) //insert n=limit record into the slotarray
            {
                RID rid = sp.insertRecord(tmpBuf); 
                System.out.println("Inserted record, RID " + rid.blockId +
                                   ", " + rid.slotNum);
                rid = sp.nextRecord(rid);
            }

            if (sp.empty())
                throw new TestFailedException("The block cannot be empty");
            
            RID rid = sp.firstRecord(); //loop through the header to get every entry
            while (rid != null)
            {
                tmpBuf = sp.getRecord(rid); 
                System.out.println("Retrieved record, RID " + rid.blockId +
                                   ", " + rid.slotNum);
                rid = sp.nextRecord(rid);
            }
           //Delete the last record
//            RID lastRid = sp.lastRid();
//            sp.deleteRecord(lastRid);
//            System.out.println("Deleted last record, RID " + lastRid.blockId +
//                    ", " + lastRid.slotNum);
            //Delete the first record
            RID tempRid = sp.firstRecord();
            sp.deleteRecord(tempRid); 
            System.out.println("Deleted record, RID " + tempRid.blockId +
                                   ", " + tempRid.slotNum);
            //add another record
            RID ret = sp.insertRecord(tmpBuf);
            System.out.println("Inserted record, RID " + ret.blockId +
                    ", " + ret.slotNum);
            
            sp.dumpBlock();
        }
    }
    public static class Test3 implements Testable
    {
    	public void test() throws Exception
        {
    		int buffSize = 20; //size of record
            int limit = 35; //number of records to add
            byte[] tmpBuf = new byte[buffSize]; //construct record in byte array

            SlottedBlock sp = new SlottedBlock(new Block()); //initialize a slotted block
            sp.init();
            sp.setBlockId(7);
            sp.setNextBlockId(8);
            sp.setPrevBlockId(SlottedBlock.INVALID_BLOCK);

            System.out.println("--- Test 3: Insert, delete and traversal of " +
                               "records ---");
            for (int i=0; i < limit; i++) //insert n=limit record into the slotarray
            {
                RID rid = sp.insertRecord(tmpBuf); 
                System.out.println("Inserted record, RID " + rid.blockId +
                                   ", " + rid.slotNum);
                rid = sp.nextRecord(rid);
            }

            if (sp.empty())
                throw new TestFailedException("The block cannot be empty");
            
            RID rid = sp.firstRecord(); //loop through the header to get every entry
            while (rid != null)
            {
                tmpBuf = sp.getRecord(rid); 
                System.out.println("Retrieved record, RID " + rid.blockId +
                                   ", " + rid.slotNum);
                rid = sp.nextRecord(rid);
            }
            
            rid = sp.firstRecord(); //loop through the header to get every entry
            rid = sp.nextRecord(rid);
            sp.deleteRecord(rid); 
            System.out.println("Deleted record, RID " + rid.blockId +
                                   ", " + rid.slotNum);
            
            RID lastRid = sp.lastRid();
            sp.deleteRecord(lastRid);
            System.out.println("Deleted last record, RID " + lastRid.blockId +
                    ", " + lastRid.slotNum);
            lastRid = sp.lastRid();
            sp.deleteRecord(lastRid);
            System.out.println("Deleted last record, RID " + lastRid.blockId +
                    ", " + lastRid.slotNum);
            sp.dumpBlock();
        }
    }

    public static boolean runTest(Testable testObj)
    {
        boolean success = true;
        try
        {
            testObj.test();
        }
        catch (Exception e)
        {
            success = false;
            e.printStackTrace();
        }

        return success;
    }


    public static void main(String[] args)
    {
        System.out.println("Running block tests.");

         //runTest(new Test1());
         runTest(new Test2());
         //runTest(new Test3());
    }
}
