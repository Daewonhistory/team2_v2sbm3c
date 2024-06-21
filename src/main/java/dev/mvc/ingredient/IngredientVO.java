package dev.mvc.ingredient;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class IngredientVO {
//	CREATE TABLE INGREDIENT(
//			ingredno                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
//			name                          		VARCHAR2(30)		 NOT NULL
//	);
	private int ingredno;
	private String name;
	private int has_allergy;
}
