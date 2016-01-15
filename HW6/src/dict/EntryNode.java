package dict;

public class EntryNode {
	protected EntryNode next;
	protected Entry item;
	
	public EntryNode (Entry e, EntryNode en){
		this.item=e;
		this.next=en;
		
	}
	public String toString(){
		StringBuilder res=new StringBuilder();
		res.append("{ ");
		EntryNode temp=this;
		while(temp!=null){
			res.append("[");
			res.append(temp.item.key()+" "+temp.item.value());
			res.append("] ");
			temp=temp.next;
		}
		res.append("}");
		return res.toString();
	}
}
