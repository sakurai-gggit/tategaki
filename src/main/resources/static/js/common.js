
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
const drawerMenu = document.getElementById('menu');
if (drawerBtn && drawerMenu) {
	drawerBtn.addEventListener('click', () => {
		drawerMenu.classList.toggle('open');
		drawerBtn.classList.toggle('open');
	});
}


const sampleText = "「そうだ。おや、あの河原は月夜だろうか。」そっちを見ますと、青白く光る銀河の岸に、銀いろのススキが、もうまるで一面、風にさらさらさらさら、ゆれてうごいて、波を立てているのでした。";
const fonts = [
	{ id: "HannariMincho", name: "はんなり明朝" },
	{ id: "WDXL", name: "WDXL Lubrifont" }
];

const container = document.getElementById('font-list');

const fontListContent = fonts.map(font => {
	return `
	<li>
		<label style="font-family:${font.id}">
			<input type="radio" name="font" value="${font.id}">
			<span>${font.name}</span>
			<div>${sampleText}</div>
		</label>
	</li>
	`
}).join("");

container.innerHTML = fontListContent;
