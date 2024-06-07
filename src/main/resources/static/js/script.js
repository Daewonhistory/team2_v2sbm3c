document.getElementById('uploadButton').addEventListener('click', function() {
  document.getElementById('profilePictureInput').click();
});

document.getElementById('profilePictureInput').addEventListener('change', function(event) {
  const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = function(e) {
      const profileImage = document.getElementById('profileImage');
      profileImage.src = e.target.result;
      profileImage.style.display = 'block';
    };
    reader.readAsDataURL(file);
  }
});
