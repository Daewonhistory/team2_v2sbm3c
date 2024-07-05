const dropZone = document.getElementById('drop-zone');
const fileInput = document.getElementById('rest_imgs');
const preview = document.getElementById('preview');
const maxFiles = 3;
let currentFiles = [];
const allowedExtensions = ['jpeg', 'jpg', 'png', 'gif'];
const allowedMIMETypes = ['image/jpeg', 'image/png', 'image/gif'];

dropZone.addEventListener('click', () => {
  fileInput.click();
});

dropZone.addEventListener('dragover', (event) => {
  event.preventDefault();
  dropZone.classList.add('dragover');
});

dropZone.addEventListener('dragleave', () => {
  dropZone.classList.remove('dragover');
});

dropZone.addEventListener('drop', (event) => {
  event.preventDefault();
  dropZone.classList.remove('dragover');
  const files = Array.from(event.dataTransfer.files);
  handleFiles(files);
});

fileInput.addEventListener('change', (event) => {
  const files = Array.from(event.target.files);
  handleFiles(files);
});

function handleFiles(files) {
  files.forEach(file => {
    const fileExtension = file.name.split('.').pop().toLowerCase();
    if (!allowedExtensions.includes(fileExtension) || !allowedMIMETypes.includes(file.type)) {
      alert('이미지 파일만 업로드할 수 있습니다! (JPEG, PNG, GIF)');
      return;
    }

    if (currentFiles.length >= maxFiles) {
      alert(`최대 ${maxFiles}개의 이미지 파일만 업로드할 수 있습니다!`);
      return;
    }

    currentFiles.push(file);
    const reader = new FileReader();
    reader.onload = (event) => {
      const previewItem = document.createElement('div');
      previewItem.classList.add('preview-item');
      const img = document.createElement('img');
      img.src = event.target.result;

      const removeBtn = document.createElement('button');
      removeBtn.classList.add('remove-btn');
      removeBtn.textContent = 'X';
      removeBtn.addEventListener('click', () => {
        preview.removeChild(previewItem);
        currentFiles = currentFiles.filter(f => f !== file);
        updateFileInputs();
      });

      previewItem.appendChild(img);
      previewItem.appendChild(removeBtn);
      preview.appendChild(previewItem);
    };
    reader.readAsDataURL(file);
  });
  updateFileInputs();
}

function updateFileInputs() {
  const inputs = ['file1MF', 'file2MF', 'file3MF'].map(id => document.getElementById(id));
  inputs.forEach(input => input.value = ''); // Clear all inputs first
  currentFiles.forEach((file, index) => {
    const dataTransfer = new DataTransfer();
    dataTransfer.items.add(file);
    inputs[index].files = dataTransfer.files;
  });
}
