function selectMidArea(selectedMidArea) {
  fetch(
      "/botarea/botarea_list",
      {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ "midareano": selectedMidArea.value })
      }
  )
      .then(response => response.json())
      .then(rdata => {
        const divBotAreas = document.getElementById("botarea_div");
        divBotAreas.innerHTML = ''; // Clear previous content

        // Create select element
        let selectElement = document.createElement("select");
        selectElement.name = "botareano"
        selectElement.id = "botarea";
        selectElement.classList.add('form-control');




        // Create default option for "All areas"

        $(document).ready(function () {
          $('select').select2({
            placeholder: '검색해주세요',
            allowClear: false,
            language: {
              noResults: function () {
                return '결과가 없습니다'; // 결과가 없을 때 표시할 메시지
              }
            }
          });
        });




        // Populate select options with bot area data
        rdata.forEach(botAreaVO => {
          let option = document.createElement("option");
          option.value = botAreaVO.botareano;
          option.textContent = botAreaVO.name;
          selectElement.appendChild(option);
        });

        // Append select element to divBotAreas
        divBotAreas.appendChild(selectElement);

        // Display botarea_div
        divBotAreas.style.display = "block";

        let address = document.getElementById('addresshi');
        address.style.display = "block";

        let findbutton = document.getElementById('findbutton');
        findbutton.style.display = "block";
      })
      .catch(error => {
        console.error('Error fetching botarea_list:', error);
        // Handle error (e.g., display error message to user)
      });
}

function selectBotarea(selectedBotArea) {
  console.log("Selected bot area:", selectedBotArea.value);
  // Additional logic here if needed
}
