-- Create enum types
CREATE TYPE promotion_type AS ENUM ('PERCENTAGE', 'FIXED');
CREATE TYPE coupon_status AS ENUM ('ACTIVE', 'USED', 'EXPIRED');

-- Create promotions table
CREATE TABLE promotions (
    id UUID PRIMARY KEY,
    event_id UUID NOT NULL,
    code VARCHAR(20) NOT NULL,
    type promotion_type NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    max_uses INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_promotions_event FOREIGN KEY (event_id) 
        REFERENCES events(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT uk_promotions_event_code UNIQUE(event_id, code),
    CONSTRAINT chk_promotions_amount CHECK (amount > 0),
    CONSTRAINT chk_promotions_dates CHECK (end_date > start_date)
);

-- Create referral_usages table
CREATE TABLE referral_usages (
    id UUID PRIMARY KEY,
    referrer_id UUID NOT NULL,
    user_id UUID NOT NULL,
    code VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_referral_usages_referrer FOREIGN KEY (referrer_id) 
        REFERENCES users(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_referral_usages_user FOREIGN KEY (user_id) 
        REFERENCES users(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT uk_referral_usages_user UNIQUE(user_id)
);

-- Create coupons table
CREATE TABLE coupons (
    id UUID PRIMARY KEY,
    code VARCHAR(20) NOT NULL,
    user_id UUID NOT NULL,
    discount_percentage INTEGER NOT NULL,
    status coupon_status NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_coupons_user FOREIGN KEY (user_id) 
        REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT uk_coupons_code UNIQUE(code),
    CONSTRAINT chk_coupons_percentage CHECK (discount_percentage > 0 AND discount_percentage <= 100)
);

-- Create point_usages table
CREATE TABLE point_usages (
    id UUID PRIMARY KEY,
    transaction_id UUID NOT NULL,
    point_id UUID NOT NULL,
    points_used INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_point_usages_transaction FOREIGN KEY (transaction_id) 
        REFERENCES transactions(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_point_usages_point FOREIGN KEY (point_id) 
        REFERENCES points(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT chk_point_usages_points CHECK (points_used > 0)
);

-- Create indexes
CREATE INDEX idx_promotions_code ON promotions(code);
CREATE INDEX idx_promotions_dates ON promotions(start_date, end_date);
CREATE INDEX idx_coupons_status ON coupons(status);
CREATE INDEX idx_coupons_expiry ON coupons(expiry_date);