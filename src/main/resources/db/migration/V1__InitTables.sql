CREATE TABLE "User" (
  "id" id,
  "name" text,
  "email" text,
  "phone" number,
  "password" varchar(255),
  "referral" number,
  "profile_picture" VARCHAR(255),
  "Role" varchar,
  "created_at" timestamp,
  "update_at" timestamp
);

CREATE TABLE "Tickets" (
  "id" id,
  "ticket_type" text,
  "ticket_quota" number,
  "ticket_available" number,
  "seat_number" number,
  "date" datetime,
  "price" decimal,
  "referral_code" number,
  "ticket_status" varchar,
  "created_at" timestamp,
  "update_at" timestamp,
  "deleted_at" timestamp,
  "user_id" id,
  "event_id" id,
  "discount_id" number
);

CREATE TABLE "Events" (
  "id" id,
  "name" text,
  "description" text,
  "start_date" datetime,
  "end_date" datetime,
  "total_seat" number,
  "available_seat" number,
  "status" varchar,
  "created_at" timestamp,
  "update_at" timestamp,
  "deleted_at" timestamp,
  "category_id" id,
  "organizer_id" id,
  "location_id" id
);

CREATE TABLE "Organizers" (
  "id" id,
  "name" text,
  "email" text,
  "description" text,
  "phone_number" number,
  "social_media" json,
  "picture" varchar,
  "created_at" timestamp,
  "update_at" timestamp,
  "deleted_at" timestamp,
  "user_id" id
);

CREATE TABLE "Transaction" (
  "id" id,
  "total_price" number,
  "date" datetime,
  "total_ticket" number,
  "created_at" timestamp,
  "update_at" timestamp,
  "deleted_at" timestamp,
  "user_id" id,
  "organizer_id" id,
  "ticket_id" id,
  "discount_id" id,
  "poin_id" id
);

CREATE TABLE "Discount" (
  "id" id,
  "date_used" timestamp,
  "amount" number,
  "status" text,
  "created_at" timestamp,
  "update_at" timestamp,
  "deleted_at" timestamp,
  "transaction_id" id
);

CREATE TABLE "Category" (
  "id" id,
  "name" text,
  "created_at" timestamp,
  "update_at" timestamp,
  "deleted_at" timestamp
);

CREATE TABLE "Location" (
  "id" id,
  "name" varchar,
  "address" varchar,
  "city" varchar,
  "coordinates" json,
  "created_at" timestamp,
  "update_at" timestamp,
  "deleted_at" timestamp
);

CREATE TABLE "Review" (
  "id" id,
  "rating" number,
  "description" text,
  "image" varchar,
  "status" varchar,
  "created_at" timestamp,
  "update_at" timestamp,
  "deleted_at" timestamp,
  "events_id" id,
  "user_id" id
);

CREATE TABLE "point" (
  "id" id,
  "amount" number,
  "referral" varchar,
  "created_at" timestamp,
  "update_at" timestamp,
  "deleted_at" timestamp,
  "discount_id" id
);

ALTER TABLE "Discount" ADD FOREIGN KEY ("id") REFERENCES "Tickets" ("discount_id");

ALTER TABLE "Events" ADD FOREIGN KEY ("id") REFERENCES "Tickets" ("event_id");

ALTER TABLE "Tickets" ADD FOREIGN KEY ("user_id") REFERENCES "User" ("id");

ALTER TABLE "Transaction" ADD FOREIGN KEY ("ticket_id") REFERENCES "Tickets" ("id");

ALTER TABLE "User" ADD FOREIGN KEY ("id") REFERENCES "Transaction" ("user_id");

ALTER TABLE "Category" ADD FOREIGN KEY ("id") REFERENCES "Events" ("category_id");

ALTER TABLE "Location" ADD FOREIGN KEY ("id") REFERENCES "Events" ("location_id");

ALTER TABLE "Organizers" ADD FOREIGN KEY ("id") REFERENCES "Events" ("organizer_id");

ALTER TABLE "Organizers" ADD FOREIGN KEY ("user_id") REFERENCES "User" ("id");

ALTER TABLE "Review" ADD FOREIGN KEY ("user_id") REFERENCES "User" ("id");

ALTER TABLE "Events" ADD FOREIGN KEY ("id") REFERENCES "Review" ("events_id");

ALTER TABLE "Discount" ADD FOREIGN KEY ("id") REFERENCES "poin" ("discount_id");

ALTER TABLE "point" ADD FOREIGN KEY ("id") REFERENCES "Transaction" ("poin_id");

ALTER TABLE "Transaction" ADD FOREIGN KEY ("discount_id") REFERENCES "Discount" ("id");

ALTER TABLE "Discount" ADD FOREIGN KEY ("transaction_id") REFERENCES "Transaction" ("id");
