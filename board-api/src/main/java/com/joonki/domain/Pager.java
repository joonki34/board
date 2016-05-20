package com.joonki.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pager<T> {
	private List<T> data;
	@JsonProperty("total_count")
	private long totalCount;
	private int limit;
	
	public static <T> Pager<T> newPager(List<T> data, long totalCount, int limit) {
		return new Pager<T>(data, totalCount, limit);
	}
}
