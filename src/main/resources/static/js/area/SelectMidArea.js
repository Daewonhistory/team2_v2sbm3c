function selectMidArea(selectedMidArea){
	fetch(
        "/botarea/botarea_list",
        {
          method: "POST",
          headers: {"Content-Type": "application/json"},
          body: JSON.stringify({"midareano": selectedMidArea.value})
        }
      )
        .then((response) => response.json())
        .then((rdata) => {
          const divBotAreas = document.getElementById("botarea_div");
          divBotAreas.innerHTML = '<h4>지역 소분류 선택</h4>';
          let allSelect = document.createElement("button");
          allSelect.type = "button";
		allSelect.value = 0;
		allSelect.onclick=function() {
			selectBotarea(allSelect);
		};
		allSelect.classList.add('btn_botarea');
    allSelect.textContent = "모든 지역";
    
    divBotAreas.appendChild(allSelect);
    rdata.forEach(botAreaVO => {
      const botArea = document.createElement("button");
      botArea.textContent = botAreaVO.name;
      botArea.type = "button";
      botArea.classList.add('button');
      botArea.name = "botareas";
      botArea.onclick=function() {
  			selectBotarea(botArea);
			};
			botArea.value = botAreaVO.botareano;
			botArea.classList.add('btn_botarea');
      
      divBotAreas.appendChild(botArea);
      botarea_div.style.display="block";
    });
  });
}