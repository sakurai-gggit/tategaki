
const saveBtn = document.getElementById('save-settings');
if (saveBtn) {
	saveBtn.addEventListener('click', () => {
		const selectedFont = document.querySelector('input[name="font"]:checked').value;
		localStorage.setItem("userFont", selectedFont);
		alert('保存完了');
	});
}

const savedFont = localStorage.getItem('userFont');
if (savedFont) {
	const targetElement = document.getElementById('main-tategaki');
	if (targetElement) {
		targetElement.classList.add(savedFont);
	}
}

const drawerBtn = document.getElementById('menu-button')
if (drawerBtn) {
	drawerBtn.addEventListener('click', () => {
		var drawer = document.querySelector('#menu');
		drawer.classList.toggle('open');
	});
}
