package hy.ea.workflow;

import java.util.Map;
import java.io.Serializable;

public class DocReadInfo implements Serializable {

	private static final long serialVersionUID = 6604820463233631652L;
	private Map<String, Boolean> readers; // Boolean true, already read,
											// False, not read yet
	private Map<String, OprationAuthority> oprationAuthority;

	public boolean isAllRead() {
		int alreadyReadCount = 0;
		for (Object key : readers.keySet()) {
			if (readers.get(key))
				alreadyReadCount++;
		}
		if (alreadyReadCount == readers.size())
			return true;
		else
			return false;

	}
	
	public boolean isReaderExist(String reader) {
		for (Object key : readers.keySet()) {
			if (reader.equalsIgnoreCase((String) key))
				return true;
		}
		return false;
	}
	
	public OprationAuthority getOprationAuthority(String reader) {
		return  oprationAuthority.get(reader);  
	}
	
	public void addNewReader(String reader, Boolean b ){
		readers.put(reader, b);
	}

	public void setReaderStatus(String reader, Boolean b) {
		readers.put(reader, b);
	}

	public Map<String, Boolean> getReaders() {
		return readers;
	}

	public void setReaders(Map<String, Boolean> readers) {
		this.readers = readers;
	}
	
	
	public static class OprationAuthority implements Serializable{
		private static final long serialVersionUID = 5908862504314940325L;
		private String load="";
		private String print="";
		private String transfer="";
		private String share = "";
		private String pub = "";
		
		public String getShare() {
			return share;
		}

		public void setShare(String share) {
			this.share = share;
		}

		public void setTransfer(String transfer) {
			this.transfer = transfer;
		}

		public boolean isAllowLoad() {
		     if(load.equalsIgnoreCase("on")){ 
                return true;
		    }else {
		    	return false;
		    }
		}
		
		public boolean isAllowPrint() {
		     if(print.equalsIgnoreCase("on")){ 
               return true;
		    }else {
		    	return false;
		    }
		}
		
		public boolean isAllowTransfer() {
		     if(transfer.equalsIgnoreCase("on")){ 
               return true;
		    }else {
		    	return false;
		    }
		}
		
		
		public String getLoad() {
			return load;
		}

		public void setLoad(String load) {
			this.load = load;
		}

		public String getPrint() {
			return print;
		}

		public void setPrint(String print) {
			this.print = print;
		}

		public String getTransfer() {
			return transfer;
		}

		public String getPub() {
			return pub;
		}

		public void setPub(String pub) {
			this.pub = pub;
		}
	  
	}


	public Map<String, OprationAuthority> getOprationAuthority() {
		return oprationAuthority;
	}

	public void setOprationAuthority(
			Map<String, OprationAuthority> oprationAuthority) {
		this.oprationAuthority = oprationAuthority;
	}

}
