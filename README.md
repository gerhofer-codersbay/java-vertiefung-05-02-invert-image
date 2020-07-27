# Java 

## Image invertieren

Damit eine Aufgabe sich gut für paralelle Programmierung/Multithreading eignet, ist es von Vorteil, wenn das Problem einfach in kleinere Teile zerlegt werden kann, die dann selbstständig und abhängig voneinander abgearbeitet werden können. 
Auf [Wikipedia](https://en.wikipedia.org/wiki/Embarrassingly_parallel) gibt es einen kurzen Artikel darüber, welche Probleme am besten für parallele Programmierung geeignet sind. 
Einige Anwendungen sind Beispiele aus der Bildverarbeitung. 

In diesem Beispiel gilt es eine Bild-Datei einzulesen und zu invertieren. 
Diese Aufgabe kann perfekt zerlegt werden, da wir lediglich die Werte eines Pixels ändern abhängig von seinem Wert.
Das kann im Prinzip für alle Pixel gleichzeitig geschehen - vielleicht erinnerst du dich daran, dass wir das bereits von der Rekursion und den Sortieralgorithmen kennen - das Divide & Conquer Prinzip. 

### Logik für die Parallelisierung

Zur Parallelisierung sollte ein `ForkJoinPool` verwendet werden. 
Ähnlich zu [diesem Tutorial](https://www.baeldung.com/java-fork-join) müsst ihr einen Threshold für die Bildgröße (Array Länge) definieren bei der die Arbeit aufgeteilt wird. 
Mittels einer Implementierung eines `ForkJoinTask`, genauer gesagt eines `RecursiveTask` implementieren wir die eigentliche Divide & Conquer und Invertier-Logik.

Mit den Thresholds zum Splitten solltet ihr bitte herumspielen und einige Zeitmessungen in einem `timing-results.md` dokumentieren.
* Wielange dauert es wenn die Arbeit nicht geteilt wird (also der Threshhold größer als die initiale Array Größe ist)
* Bei welchem Threshold erzielt ihr die besten Ergebnisse?
* Ab wann ist der geteilte Weg kleiner schlechter als wenn es lediglich ein Thread abarbeiten würde? (wegen dem Overhead der Threaderstellung)

### Logik zum Einlesen einer Bilddatei 

Mit Hilfe des [ImageUtil]() kann man die Bilddateien "city.jpg", "flower-power.jpg" bzw. "landscape.jpg" einfach einlesen. 
```java
    String filePath = "src/main/resources/city.jpg";
    File file = new File(filePath);
    BufferedImage image = ImageIO.read(file);
```
Aus einem `BufferedImage` kann man sich einfach das Array von Pixels holen: 
```java
    int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
```

### Logik zum Invertieren eines Pixels 

Für ein Pixel an der Stelle index kann man sich mittels der `Color` Klasse ihre Farbrepräsentation ansehen. 
```java
    Color color = new Color(pixels[index], true);
```

`Color` hat auch einen Konstruktor über den eine neue Farbe erstellt werden kann, Farbwert X ist invertiert 255 - X, die Int Repräsentation die für das Ergebnisbild gebraucht wird bekommt man durch Aufruf der `.getRGB()` Methode auf einer `Color`.

### Logik zum Schreiben einer Bilddatei

Hat man ein Array mit den Integer Werten für die neue Bilddatei, kann einfach eine Datei erzeugt und geschrieben werden. 
Zuerst müssen wir uns aus unserem Pixel Array ein `BufferedImage` erstellen.
```java
    int[] resultPixels = /* invertiertes Array */;
    // image ist unser eingelesenes Bild und eine Variable vom Typ BufferedImage
    BufferedImage invertedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
    invertedImage.setRGB(0, 0, invertedImage.getWidht(), invertedImage.getHeight(), resultPixels, 0, invertedImage.getWidht());
```
Und mittels des `ImageUtil` können wir dieses Bild in einem bestimmten Dateiformat speichern.
```java
    String invertedImagePath = "src/main/resources/inverted-city.png";
    File invertedImageFile = new File(invertedImagePath);
    ImageIO.write(invertedImage, "png", invertedImageFile)
```

Ich empfehle die Datei als PNG zu speichern und nicht im IntelliJ sondern mit einem Bildtool zu öffnen um zu verifizieren dass euer Code funktioniert. 
IntelliJ kann Bilddateien ohne die richtigen Header manchmal nicht öffnen, die Windows Galerie allerding schon.