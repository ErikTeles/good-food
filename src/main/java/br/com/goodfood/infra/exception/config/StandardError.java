package br.com.goodfood.infra.exception.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardError{
    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}