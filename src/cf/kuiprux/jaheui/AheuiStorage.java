package cf.kuiprux.jaheui;

import java.util.LinkedList;

public class AheuiStorage {
	
	public static final int MODE_STACK = 0;
	public static final int MODE_QUEUE = 1;
	public static final int MODE_TURNEL = 2;
	
	LinkedList<Integer> storage = new LinkedList<Integer>();
	
	private int mode;
	
	public AheuiStorage(int mode) {
		this.mode = mode;
	}
	
	public int[] pop(int amount) {
		int[] nums = null;
		if(storage.size() >= amount) {
			nums = new int[amount];
			for(int i = 0; i < amount; i++) {
				if(mode == MODE_STACK) {
					nums[i] = storage.pollFirst();
				}
				if(mode == MODE_QUEUE) {
					nums[i] = storage.pollLast();
				}
			}
		}
		return nums;
	}
	
	public void push(int num) {
		storage.push(num);
	}

	public void pushLast(int num) {
		storage.addLast(num);
	}
}
