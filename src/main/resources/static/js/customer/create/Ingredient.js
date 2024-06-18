// 재료 입력 필드를 제거하는 함수
function removeIngredient(button) {
  const ingredientDiv = button.parentElement;
  document.getElementById('ingredients').removeChild(ingredientDiv);
}

// 재료를 추가하는 함수
function addIngredient(ingredno, name) {
  const ingredientDiv = document.createElement('div');
  ingredientDiv.classList.add('ingredient');
  ingredientDiv.style = "background-color: gray; border-radius: 50%; width:150px; display: inline-block; margin: 5px;";

  const ingredientInput = document.createElement('input');
  ingredientInput.type = 'hidden';
  ingredientInput.name = 'ingredno[]';
  ingredientInput.value = ingredno;
  ingredientInput.required = true;

  const ingredientText = document.createElement('span');
  ingredientText.innerText = name;

  const removeButton = document.createElement('button');
  removeButton.type = 'button';
  removeButton.innerText = '삭제';

  removeButton.onclick = function() {
    removeIngredient(removeButton);
  };

  ingredientDiv.appendChild(ingredientInput);
  ingredientDiv.appendChild(ingredientText);
  ingredientDiv.appendChild(removeButton);

  document.getElementById('ingredients').appendChild(ingredientDiv);
}
function handleSelectChange(selectElement) {
  const selectedOption = selectElement.options[selectElement.selectedIndex];
  addIngredient(selectedOption.value, selectedOption.text);
}