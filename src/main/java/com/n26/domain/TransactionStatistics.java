package com.n26.domain;


public class TransactionStatistics {
	private String sum;
	private String avg;
	private String max;
	private String min;
	
	public String getSum() {
		return sum;
	}
	public TransactionStatistics(String sum, String avg, String max,
			String min, long count) {
		this.sum = sum;
		this.avg = avg;
		this.max = max;
		this.min = min;
		this.count = count;
	}
	public TransactionStatistics() {
		super();
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	public String getAvg() {
		return avg;
	}
	public void setAvg(String avg) {
		this.avg = avg;
	}
	public String getMax() {
		return max;
	}
	public void setMax(String max) {
		this.max = max;
	}
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
	}
	private long count;
		public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	
	
}
