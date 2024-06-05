package dev.mvc.menuingred;

import java.util.ArrayList;

import dev.mvc.ingredient.IngredientVO;
import dev.mvc.menu.MenuVO;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MenuIngredDTO {
	private MenuVO menuVO;
	private ArrayList<IngredientVO> ingredList;
}
