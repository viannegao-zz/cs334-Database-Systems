/* Uses dummy start and end fragments with head and tail value set to null
*/

public class Chromosome {
	private Fragment start;
	private Fragment end;
	private Integer numFrag;
	
	/**
	 * creates dummy start and end fragments
	 */
	public Chromosome() {
		start = new Fragment(null,end);
		end = new Fragment(start,null);
		numFrag = 0;
	}
	
	/**
	 * class for linked chromosome fragments 
	 * with type Integer head value and tail value
	 */
	private class Fragment {
		private Integer head;
		private Integer tail;
		private Fragment next;
		private Fragment prev;
		
		public Fragment (Fragment prev, Fragment next) {
			this.head = null;
			this.tail = null;
			this.prev = prev;
			this.next = next;
		}
		public Fragment (Integer head, Integer tail, Fragment prev, Fragment next){
			this.head = head;
			this.tail = tail;
			this.prev = prev;
			this.next = next;
		}
	}
	
	/**
	 * creates String with all fragments in chromosome
	 * printed in a list in a readable fashion
	 * @ret String - chromosome sequence
	 */
	public String toString() {
		String ret = "[";
		Fragment cur = start;
		while (cur.next.next != null) {
			ret += cur.next.head + "--" + cur.next.tail +"|";
			cur = cur.next;
		}
		ret += "]";
		return ret;
	}
	
	/**
	 * add fragments to the end of the chromosome
	 * @param num -number of fragments to add
	 */
	public void add (Integer num) {
		for (int i = 0; i < num; i++) {
			Fragment newF = new Fragment(2*numFrag, 2*numFrag+1, end.prev, end);
			end.prev.next = newF;
			end.prev = newF;
			numFrag += 1;			
		}
	}
	
	/**
	 * delete fragment
	 * @param frag - which fragment to delete
	 */
	public void delete (Integer frag) {
		Fragment toDel = start;
		for (int i = 0; i < frag; i++) {
			toDel = toDel.next;
		}
		toDel.prev.next = toDel.next;
		toDel.next.prev = toDel.prev;
		toDel.prev = null;
		toDel.next = null;
	}
	/**
	 * randomly delete a number of fragments
	 * @param numDel
	 * @param numFrag
	 * @return
	 */
	public Integer randomDel(Integer numDel, Integer numFrag){
		for (int i=0; i<numDel; i++){
			int toDel = (int )(Math.random() * (numFrag) + 1);
			this.delete(toDel);
			numFrag -= 1;
		}
		return numFrag;
	}
	/**
	 * invert fragment
	 * @param frag - which fragment to invert
	 */
	public void invert (Integer frag) {
		Fragment toInv = start;
		for (int i=0; i < frag; i++) {
			toInv = toInv.next;
		}
		Integer temp = toInv.head;
		toInv.head = toInv.tail;
		toInv.tail = temp;
	}
	
	/**
	 * randomly invert a number of fragments
	 * @param numInv
	 * @param numFrag
	 */
	public void randomInv (Integer numInv, Integer numFrag) {
		for (int i=0; i < numInv; i++){
			int toInv = (int )(Math.random() * (numFrag) + 1);
			this.invert(toInv);
		}
	}

//	/**
//	 * duplicate fragment
//	 * @param frag - which fragment to duplicate
//	 */
//	public void duplicate (Integer frag) {
//		Fragment toDup = start;
//		for (int i =0; i<frag; i++) {
//			toDup = toDup.next;
//		}
//		Fragment dup = new Fragment(toDup.head, toDup.tail, toDup, toDup.next);
//		toDup.next.prev = dup;
//		toDup.next = dup;
//	}
	/**
	 * swap the data stored in two fragments to create the effect of rearrangement
	 * @param firstFrag
	 * @param secondFrag
	 */
	public void swap (int firstFrag, int secondFrag) {
		Fragment frag1 = start;
		Fragment frag2 = start;
		for (int i =0; i< firstFrag; i++) {
			frag1 = frag1.next;
		}
		for (int j=0; j<secondFrag; j++){
			frag2 = frag2.next;
		}
		Integer temph = frag1.head;
		Integer tempt = frag1.tail;
		frag1.head = frag2.head;
		frag1.tail = frag2.tail;
		frag2.head = temph;
		frag2.tail = tempt;
	}
	/**
	 * Use swap to randomly shuffle fragments
	 * @param num_swaps
	 * @param fragnum
	 */
	public void randomJoin (Integer num_swaps, Integer fragnum) {
		for (int i=0; i < num_swaps; i++){
			int toSwap1 = (int )(Math.random() * (fragnum) + 1);
			int toSwap2 = (int )(Math.random() * (fragnum) + 1);
			this.swap (toSwap1,toSwap2);
		}
		
	}
//	public String countJoints() {
//		Fragment cur = start;
//		Integer countDel = 0;
//		Integer countTR = 0;
//		Integer countH2H = 0;
//		Integer countT2T = 0;
//		while (cur.next != null) {
//			cur = cur.next;
//			if (cur.head > cur.tail) {
//				countH2H += 1;
//				countT2T += 1;
//			}
//			if ()
//		}
//	}
	
	public static void main(String[] args) {
		Chromosome test = new Chromosome();
		Integer fragnum = 30;
		test.add(fragnum); //build a chromosome with many fragments
		fragnum = test.randomDel(fragnum/4, fragnum); //randomly delete 1/4 fragments
		test.randomInv(fragnum/4, fragnum); //randomly delete 1/4 fragments
		test.randomJoin(fragnum/4, fragnum); //create a randomly joined chromosome by randomly swapping 1/2 of the fragments
		System.out.println (test);
	}
}