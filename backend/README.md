# Backend приложения Muse (HSK/CSCA)

Serverless-бэкенд на AWS: API Gateway (HTTP API) + Lambda (Python) + DynamoDB + Cognito.

## Что тут

- `template.yaml` — вся инфраструктура (SAM): таблица DynamoDB, Lambda, HTTP API, Cognito User Pool
- `src/app.py` — обработчик API: `/content/version`, `/courses`, `/chapters`, `/lessons`
- `seed/seed.py` — загрузка демо-контента в DynamoDB

## Установка инструментов (один раз)

```bash
cd backend
python3 -m venv .venv
source .venv/bin/activate
pip install awscli aws-sam-cli
```

## Настройка доступа к AWS (один раз)

1. В консоли AWS: IAM → Users → ваш пользователь → Security credentials → Create access key
2. В терминале:

```bash
aws configure
# AWS Access Key ID: <ключ>
# AWS Secret Access Key: <секрет>
# Default region name: eu-north-1
# Default output format: json
```

## Деплой

```bash
cd backend
source .venv/bin/activate
sam build
sam deploy --guided   # при первом разе: stack name = muse-backend, region = eu-north-1,
                      # остальное — Enter; "allow without authorizer" = y
```

После деплоя SAM выведет `ApiUrl` — его нужно подставить в приложение
в `RetrofitClient.kt` (константа `BASE_URL`).

## Наполнение базы контентом

```bash
python3 seed/seed.py --table muse-content --region eu-north-1
```

## Проверка

```bash
curl https://<api-id>.execute-api.eu-north-1.amazonaws.com/content/version
curl https://<api-id>.execute-api.eu-north-1.amazonaws.com/courses
```

## Обновление контента

Поменять `CONTENT_VERSION` и данные в `seed/seed.py`, запустить скрипт ещё раз.
Приложение само увидит новую версию и подтянет изменения при синхронизации.
