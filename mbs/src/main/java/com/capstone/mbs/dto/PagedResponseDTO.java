package com.capstone.mbs.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PagedResponseDTO<T> {
	
	private List<T> content;
	private int pageNumber;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean lastPage;
	private List<SortOrder> sort;
	
	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class SortOrder {
		private String property;
		private String direction;
	}
	
}
