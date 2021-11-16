# HB QA Quiz

## Bağımlılıklar

- Java
- Maven
- TestNG
- Allure


## Kurulum

1. Repositoriyi klonlayın

```shell
git clone https://github.com/Fnur17/HB_CaseStudy
```

2. Maven bağımlılıklarını kurun.

```shell
mvn compile
```

3. data.json dosyasına gerekli test datasını girin.


## Testlerin Çalıştırılması

**Chrome Browser için;**
```shell
DRIVER=chrome mvn test
```

**Firefox Browser için;**

```shell
DRIVER=firefox mvn test
```


Eğer browser seçilmez ise default olarak chrome ile çalışacaktır.


## Raporlama


Testler çalıştırıldıktan sonra  `./allure-results` klasöründe otomatik olarak oluşur.

Raporları html formatında görüntüleyebilmek için aşağıdaki linkten lokal ortamınıza kurulum adımlarını (https://docs.qameta.io/allure/)

Allure Fw ü lokal ortamınıza kurduktan sonra  `allure serve` komutuyla raporları html olarak görüntüleyebilirsiniz.
