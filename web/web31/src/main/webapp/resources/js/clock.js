function updateClock() {
    const now = new Date();
    const formattedTime = now.toLocaleDateString() + " " + now.toLocaleTimeString();
    document.getElementById("timeDisplay").innerText = formattedTime;
}

setInterval(updateClock, 9000);

window.onload = updateClock;
