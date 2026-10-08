package com.cocina.robocook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Paginated response wrapper")
public class PageResponseDTO<T>{

    @Schema(description = "Content of the current page")
    private List<T> content;

    @Schema(description = "Current page number (0-indexed)", example = "0")
    private int currentPage;

    @Schema(description = "Page size", example = "20")
    private int pageSize;

    @Schema(description = "Total number of elements", example = "150")
    private long totalElements;

    @Schema(description = "Total number of pages", example = "8")
    private int totalPages;

    @Schema(description = "Is this the last page?", example = "false")
    private boolean isLastPage;

    @Schema(description = "Is this the first page?", example = "true")
    private boolean isFirstPage;

    @Schema(description = "Is content empty?", example = "false")
    private boolean isEmpty;

    /**
     * Constructor from Spring Page object
     */
    public static <T> PageResponseDTO<T> from(Page<T> page) {
        return PageResponseDTO.<T>builder()
                .content(page.getContent())
                .currentPage(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .isLastPage(page.isLast())
                .isFirstPage(page.isFirst())
                .isEmpty(page.isEmpty())
                .build();
    }
}
