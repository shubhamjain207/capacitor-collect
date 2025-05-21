import { CollectDataPlugin } from 'capacitor-collect-data';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    CollectDataPlugin.echo({ value: inputValue })
}
