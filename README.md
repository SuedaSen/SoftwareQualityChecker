# SER Requirement Checker (Java)

Yazılım gereksinimlerinde **belirsizlik** ve **tutarsızlık** adaylarını tespit eden uygulama. Web arayüzü ve CLI destekler.

**Detaylı proje açıklaması (amaçlar, ne yapıldı, teknikler):** [PROJE.md](PROJE.md)

## Gereksinimler

- **JDK 17+**
- **Maven 3.6+** (yoksa: `brew install maven` veya [maven.apache.org](https://maven.apache.org/download.cgi))

Java’da Python’daki gibi “venv” yok; bağımlılıklar Maven ile proje içinde yönetilir (`pom.xml`). İlk `mvn` çalıştırmasında bağımlılıklar indirilir.

## Derleme

```bash
cd ser-java
mvn -q package
```

## Web arayüzü

```bash
mvn spring-boot:run
```

Tarayıcıda: **http://localhost:8080**

## CLI (rapor üretmek)

```bash
mvn -q exec:java -Dexec.mainClass="com.ser.reqcheck.Cli" \
  -Dexec.args="--input data/sample_requirements.csv --format csv --out reports/report.md"
```

JSON rapor:

```bash
mvn -q exec:java -Dexec.mainClass="com.ser.reqcheck.Cli" \
  -Dexec.args="--input data/sample_requirements.csv --format csv --out reports/report.json --out-format json"
```

Önce `reports` klasörünü oluştur: `mkdir -p reports`

## Tek jar ile çalıştırma

```bash
mvn -q package
java -jar target/reqcheck-1.0.0.jar
```

Web arayüzü açılır (port 8080). CLI için yukarıdaki `exec:java` kullanın.
