/**
 * 
 */
package database;

/**
 * @author david184.tsang
 *
 */
public class LogEvent{
	
	private String desc;
	private long id;	    
	private String tag;
	private long utctime;
	
	public void setdesc(String  descIn)
	{
	    this.desc= descIn;
	}

	public String getdesc() {
	    	return desc;
	}
	public void setid(long  idIn)
	{
	    this.id= idIn;
	}
	
	public boolean enabled()
	{
		boolean ret = false;
		
		if (desc.equals("true")) {
			ret = true;
		}
		
		return ret;
	}

	public long getid() {
	    	return id;
	}
	public void settag(String  tagIn)
	{
	    this.tag= tagIn;
	}

	public String gettag() {
	    	return tag;
	}
	public void setutctime(long  utctimeIn)
	{
	    this.utctime= utctimeIn;
	}

	public long getutctime() {
	    	return utctime;
	}

} 
