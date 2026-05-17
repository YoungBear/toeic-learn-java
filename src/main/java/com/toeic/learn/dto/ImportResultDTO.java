package com.toeic.learn.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportResultDTO {

    private int totalCount;
    private int successCount;
    private int failCount;
    private List<FailRecord> failRecords = new ArrayList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FailRecord {
        private int row;
        private String error;
    }

    public void addSuccess() {
        successCount++;
        totalCount++;
    }

    public void addFail(int row, String error) {
        failCount++;
        totalCount++;
        failRecords.add(new FailRecord(row, error));
    }
}
