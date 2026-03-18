
const saveBtn = document.getElementById('save-settings');
saveBtn.addEventListener('click', () => {
	const selectedFont = document.querySelector('input[name="font"]:checked').value;
	localStorage.setItem("userFont", selectedFont);
	alert('保存完了');
});


const savedFont = localStorage.getItem('userFont');
if (savedFont) {
	const targetElement = document.getElementById('main-tategaki');
	if (targetElement) {
		targetElement.classList.add(savedFont);
	}
}

