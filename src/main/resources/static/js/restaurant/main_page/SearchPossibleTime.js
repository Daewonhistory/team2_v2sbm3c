function searchPossibleTime(){
	const reserveDate = document.getElementById("reserve_date");
	const personnel = document.getElementById("personnel");
	const restno = document.getElementById("restno");
	const accessType=document.getElementById("access-type");
	console.log(reserveDate.value + " " + personnel.value + " " + restno.value);
	
	fetch(
        "/admitPerson/searchPossibleTime",
        {
          method: "POST",
          headers: {"Content-Type": "application/json"},
          body: JSON.stringify({
									"restno": restno.value,
									"personnel": personnel.value,
									"date": reserveDate.value
								})
        }
      )
        .then((response) => response.json())
        .then((rdata) => {
          const scrollContainer = document.getElementById("scrollContainer");
          scrollContainer.innerHTML = '';
          
          rdata.forEach(admitPersonVO => {
            const timeButton = document.createElement("button");
            timeButton.textContent = admitPersonVO.time + ":00";
            timeButton.dataset.admitpersonno = admitPersonVO.admitpersonno;
            timeButton.type = "button";
					  timeButton.value = admitPersonVO.admitpersonno;
					  timeButton.classList.add('btn_botarea');
            
            timeButton.addEventListener('click', function() {
							if(accessType.value != "customer"){
								alert("로그인후 예약기능을 사용해주세요.");
							}else{
								const admitpersonno = this.dataset.admitpersonno;
	              const person = document.getElementById('personnel').value;
	              window.location.href = `/reservation/create?admitpersonno=${admitpersonno}&person=${person}`;
							}
        		});
            scrollContainer.appendChild(timeButton);
            
            
      		});
    		});
}