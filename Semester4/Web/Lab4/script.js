const menu = document.createElement('div');
menu.id = 'menu';

const ul = document.createElement('ul');

for (let i = 1; i <= 5; i++) {
    const li = document.createElement('li');

    const a = document.createElement('a');
    a.href = '#';
    a.textContent = `Menu ${i}`;

    const ulSubmenu = document.createElement('ul');
    const numComponents = Math.floor(Math.random() * 3) + 3;

    for (let j = 1; j <= numComponents; j++) {
        const liComponent = document.createElement('li');

        const aComponent = document.createElement('a');
        aComponent.href = '#';
        aComponent.textContent = `Component ${j}`;

        liComponent.appendChild(aComponent);
        ulSubmenu.appendChild(liComponent);
    }

    li.appendChild(a);
    li.appendChild(ulSubmenu);
    ul.appendChild(li);
}

menu.appendChild(ul);
document.body.appendChild(menu);