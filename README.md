# Subscriptions API

## Эндпоинты


### 1. Подписка на пользователя

- **URL:** `/users/{userId}/subscriptions/{followingId}`
- **Метод:** `POST`
- **Параметры:**
    - `userId` (long) - ID пользователя, который подписывается.
    - `followingId` (long) - ID пользователя, на которого подписываются.
- **Ответ:**
    - `SubscriptionDto` - Данные о созданной подписке.

#### Пример запроса:

```http
POST /users/1/subscriptions/2
```

#### Пример ответа:

```json
{
  "id": 1,
  "follower": {
    "id": 1,
    "name": "Earl Wilkinson III"
  },
  "following": {
    "id": 2,
    "name": "Timothy Hettinger"
  },
  "created": "2024-11-03T02:21:09"
}
```

---

### 2. Отписка от пользователя

- **URL:** `/users/{userId}/subscriptions/{followingId}`
- **Метод:** `DELETE`
- **Параметры:**
    - `userId` (long) - ID пользователя, который отписывается.
    - `followingId` (long) - ID пользователя, от которого отписываются.
- **Ответ:**
    - `204 No Content` - Успешное выполнение операции. Тело ответа пустое.

#### Пример запроса:

```http
DELETE /users/1/subscriptions/2
```

---

---

### 3. Получение списка пользователей, на которых подписан пользователь

- **URL:** `/users/{userId}/subscriptions/following`
- **Метод:** `GET`
- **Параметры:**
  - `userId` (long) - ID пользователя, для которого необходимо получить список подписок.
  - `from` (int, необязательный) - Индекс первого элемента, который необходимо вернуть. По умолчанию `0`.
  - `size` (int, необязательный) - Количество элементов, которые необходимо вернуть. По умолчанию `10`.
- **Ответ:**
  - `Set<UserShortDto>` - Множество пользователей, на которых подписан указанный пользователь.

#### Пример запроса:

```http
GET /users/1/subscriptions/following?from=0&size=10
```

#### Пример ответа:

```json
[
  {
    "id": 9,
    "name": "Brittany Padberg"
  },
  {
    "id": 10,
    "name": "Ruth Schamberger"
  },
  {
    "id": 8,
    "name": "Eloise Jerde"
  }
]
```

---

### 4. Получение списка подписчиков пользователя

- **URL:** `/users/{userId}/subscriptions/followers`
- **Метод:** `GET`
- **Параметры:**
  - `userId` (long) - ID пользователя, для которого необходимо получить список подписчиков.
  - `from` (int, необязательный) - Индекс первого элемента, который необходимо вернуть. По умолчанию `0`.
  - `size` (int, необязательный) - Количество элементов, которые необходимо вернуть. По умолчанию `10`.
- **Ответ:**
  - `Set<UserShortDto>` - Множество пользователей, подписанных на указанный пользователь.

#### Пример запроса:

```http
GET /users/1/subscriptions/followers?from=0&size=10
```

#### Пример ответа:

```json
[
  {
    "id": 13,
    "name": "Carlos Ruecker"
  },
  {
    "id": 14,
    "name": "Lamar Larson"
  },
  {
    "id": 12,
    "name": "Justin Hand PhD"
  }
]
```
