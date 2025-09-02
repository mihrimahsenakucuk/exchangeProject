
Türkiye Cumhuriyeti Merkez Bankası'ndan kur bilgilerini alarak, listeleme, para birimi dönüşümü için geliştirilmiş Spring Boot projesidir.





| HTTP Yöntemi | Endpoint                               | Açıklama                                 |
| ------------ | -------------------------------------- | ---------------------------------------- |
| GET          | `/api/exchange-rates`                  | Güncel döviz kurlarını TCMB'den çeker    |
| GET          | `/api/exchange-rates/by-code?code=USD` | Belirli koda ait kuru TCMB'den çeker     |
| GET          | `/api/exchange-rates/from-db`          | Veritabanındaki kayıtlı kurları listeler |
| GET          | `/api/exchange-rates/convert`          | Kur çevrimi yapar (USD -> EUR gibi)      |
| POST         | `/api/exchange-rates/add`              | Yeni döviz kuru ekler                    |

{
  "code": "USD",
  "buying": 41.16,
  "selling": 41.98
}
