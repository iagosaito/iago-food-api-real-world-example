package br.algaworks.Iago.Food.core.validation;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

    //DataSize é uma classe que representa o tamanho de um dado
    private DataSize maxSize;

    @Override
    public void initialize(FileSize constraintAnnotation) {
        this.maxSize = DataSize.parse(constraintAnnotation.maxSize());
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext constraintValidatorContext) {
        return value == null || value.getSize() <= this.maxSize.toBytes();
    }
}
