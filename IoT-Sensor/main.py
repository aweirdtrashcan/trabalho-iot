from machine import Pin, Signal, ADC, Timer
import network
import urequests
from time import sleep
adc = ADC(33)

led = Signal(Pin(12, Pin.OUT))

MAX_HISTORY = 250

history = []
beat = False
beats = 0

def connect_wifi():
    ssid = 'Diego-2G'
    #password = 'yK1hJdXZWr'
    password = '70854579'
    station = network.WLAN(network.STA_IF)

    station.active(True)
    station.connect(ssid, password)

    print('Conectando a rede ' + ssid, end = "")

    while station.isconnected() == False:
        pass

    if station.isconnected() == True:
        print('\nConectado com sucesso')
        print(station.ifconfig())

def post_beat(beat):
    urequests.post(url = 'http://192.168.15.108:8080/bpm', headers = {'content-type': 'application/json'}, data = str(beat))

def calculate_bpm(t):
    global beats
    print('BPM:', beats * 6)
    post_beat(beats * 6)
    beats = 0

connect_wifi()
timer = Timer(1)
timer.init(period=10000, mode=Timer.PERIODIC, callback=calculate_bpm)

while True:
    v = adc.read()
    #print(v)

    sleep(0.2)

    history.append(v)

    history = history[-MAX_HISTORY:]

    minima, maxima = min(history), max(history)

    threshold_on = (minima + maxima * 3) // 4
    threshold_off = (minima + maxima) // 2

    if not beat and v > threshold_on:
        beat = True
        beats += 1
        led.on()

    if beat and v < threshold_off:
        beat = False
        led.off()
