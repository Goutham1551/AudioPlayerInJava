# AudioPlayerInJava 🎵

A sleek and responsive desktop audio player built with Java and Swing.

## 🔍 Overview

AudioPlayerInJava is a lightweight Java Swing application that allows users to load and play audio files through a clean, intuitive interface. It supports WAV playback, featuring essential controls like play, pause/resume, stop, and a progress slider that reflects playback in real time.

## ⚙️ Features

* **Play / Pause / Resume / Stop**: Full control over audio playback.
* **Progress Tracking**: Slider indicator and time display (current vs. total duration).
* **File Loading**: Easily open audio files mid-playback.
* **Responsive UI**: Built with Swing and Java threading—smooth and non-blocking.

## 🧩 Architecture & Tech Stack

* **Languages & Libraries**: Java SE, Swing, Java Sound API; JavaFX optional for enhanced MP3 support.
* **Core Components**:

  1. `AudioPlayer.java` – Manages playback (play, pause, resume, stop) using a background thread.
  2. `PlayingTimer.java` – Updates slider and time labels while the audio plays.
  3. `SwingAudioPlayer.java` – Main GUI frame tying components together.
* **Concurrency**: Playback runs on separate threads to keep the UI responsive.

## 💻 Getting Started

### Prerequisites

* Java 11+ (or higher)
* \[Optional] JavaFX SDK if playing MP3 files via JavaFX

### Setup & Running

1. **Clone the repo:**

   ```bash
   git clone https://github.com/Goutham1551/AudioPlayerInJava.git
   cd AudioPlayerInJava
   code . // if you use vs code 
   ```
2. **Compile the source code:**

   ```bash
   javac -cp . src/*.java
   ```
3. **Run the application:**

   ```bash
   java -cp src SwingAudioPlayer
   ```

*For MP3 support via Java Swing:*

```bash
javac --module-path /path/to/javafx/lib --add-modules javafx.media -cp . src/*.java
java --module-path /path/to/javafx/lib --add-modules javafx.media -cp src SwingAudioPlayer
```

### Usage

* Click **Open** to select an audio file (WAV).
* Use **Play**, **Pause/Resume**, **Next/Prev** buttons to control playback.
* Monitor the **progress slider** and **time labels**.
* Load a new file anytime—playback will stop and the new file will load promptly.
* Add Music on local desktop aslo

## 🛠️ Extending the Project

* **Add playlist capability persistance**
* **Integrate with MP3 decoder libraries** (e.g., JLayer, Tritonus)
* **Enhance UI**: add icons, themes, drag-and-drop support

## 👤 Contributing

Contributions are welcome! Please feel free to:

1. Fork the repository
2. Create a feature branch (`git checkout -b feat/your-feature`)
3. Commit your enhancements (`git commit -m 'feat: describe feature'`)
4. Push to your branch (`git push origin feat/your-feature`)
5. Open a pull request

## 📄 License

MIT License – feel free to use, modify, and distribute.

---

