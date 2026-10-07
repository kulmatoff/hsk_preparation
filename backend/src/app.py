"""Lambda-обработчик API контента приложения Muse.

Эндпоинты (ровно те, что ждёт Android-приложение в ApiService.kt):
  GET /content/version -> {"version": int}
  GET /courses         -> {"courses": [{"id", "title"}]}
  GET /chapters        -> {"chapters": [{"id", "courseId", "title", "position"}]}
  GET /lessons         -> {"lessons": [{"id", "chapterId", "courseId",
                                        "chapterTitle", "chapterPosition",
                                        "title", "content", "position"}]}
"""

import json
import os
from decimal import Decimal

import boto3
from boto3.dynamodb.conditions import Key

table = boto3.resource("dynamodb").Table(os.environ["CONTENT_TABLE"])


class DecimalEncoder(json.JSONEncoder):
    """DynamoDB возвращает числа как Decimal — конвертируем в int/float."""

    def default(self, obj):
        if isinstance(obj, Decimal):
            return int(obj) if obj % 1 == 0 else float(obj)
        return super().default(obj)


def response(status: int, body: dict) -> dict:
    return {
        "statusCode": status,
        "headers": {"Content-Type": "application/json"},
        "body": json.dumps(body, cls=DecimalEncoder, ensure_ascii=False),
    }


def query_entity(entity: str) -> list:
    items = []
    kwargs = {"KeyConditionExpression": Key("entity").eq(entity)}
    while True:
        page = table.query(**kwargs)
        items.extend(page.get("Items", []))
        if "LastEvaluatedKey" not in page:
            break
        kwargs["ExclusiveStartKey"] = page["LastEvaluatedKey"]
    for item in items:
        item.pop("entity", None)
    return items


def handler(event, context):
    path = event.get("rawPath", "")

    if path == "/content/version":
        item = table.get_item(Key={"entity": "meta", "id": 1}).get("Item")
        return response(200, {"version": int(item["version"]) if item else 0})

    if path == "/courses":
        courses = sorted(query_entity("course"), key=lambda c: c["id"])
        return response(200, {"courses": courses})

    if path == "/chapters":
        chapters = sorted(
            query_entity("chapter"), key=lambda c: (c["courseId"], c["position"])
        )
        return response(200, {"chapters": chapters})

    if path == "/lessons":
        lessons = sorted(
            query_entity("lesson"),
            key=lambda l: (l["courseId"], l["chapterPosition"], l["position"]),
        )
        return response(200, {"lessons": lessons})

    return response(404, {"message": f"Unknown path: {path}"})
