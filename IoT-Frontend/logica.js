let contagem = 0;
let text = document.getElementById('user_information');

const calcular_perigo = (batimento) => {
    if (batimento > 100) {
        return `O seu batimento cardíaco é de: ${batimento} BPM. Agora confira a tabela abaixo:`;
    } else if (batimento > 140) {
        return `O seu batimento cardíaco é de: ${batimento} BPM. Agora confira a tabela abaixo:`;
    } else if (batimento > 40 && batimento < 101) {
        return `O seu batimento cardíaco é de: ${batimento} BPM. Agora confira a tabela abaixo:`;
    } else if (batimento < 40 && batimento > 10) {
        return `O seu batimento cardíaco é de: ${batimento} BPM. Agora confira a tabela abaixo:`;
    } else {
        return "Não foi possível calcular seu batimento cardíaco. Confira se seu dedo está pressionando contra o sensor.";
    }
}

const succ = (json) => {
    text.innerHTML = calcular_perigo(json.beat);

}
const err = (reason) => {
    text.innerHTML = calcular_perigo(0);
}

const getData = async () => {
    const url = "http://192.168.15.108:8080/bpm";
    const resp = await fetch(url);

    resp.json().then(succ, err);
}

setInterval(getData, 1000);