# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table account_bank_admin (
  id                            bigint auto_increment not null,
  level                         integer,
  admin_id                      bigint,
  chu_tai_khoan                 varchar(255),
  so_tai_khoan                  varchar(255),
  tinh_thanh                    varchar(255),
  bank_name                     varchar(255),
  chi_nhanh                     varchar(255),
  created_at                    datetime(6),
  constraint pk_account_bank_admin primary key (id)
);

create table admin (
  id                            integer auto_increment not null,
  username                      varchar(255),
  password                      varchar(255),
  level                         integer,
  rank                          varchar(255),
  fullname                      varchar(255),
  phone                         varchar(255),
  address                       varchar(255),
  email                         varchar(255),
  status                        integer,
  balance                       bigint,
  balance_wait_payment          bigint,
  bonus                         bigint,
  bonus_wait_payment            bigint,
  bonus_introduce_customer      bigint,
  bonus_introduce_customer_wait_payment bigint,
  created                       datetime(6),
  create_by                     varchar(255),
  fcm_token                     varchar(255),
  is_active                     integer,
  work_status                   integer,
  latitude                      double,
  longitude                     double,
  position                      varchar(255),
  time_update                   datetime(6),
  link_avatar                   varchar(255),
  device                        integer,
  province                      varchar(255),
  district                      varchar(255),
  ward                          varchar(255),
  province_id                   varchar(255),
  district_id                   varchar(255),
  district_work_id              varchar(255),
  ward_id                       varchar(255),
  service_id                    varchar(255),
  han_muc                       integer,
  so_tien_da_tru_am             integer,
  birthday                      varchar(255),
  salary                        integer,
  salary_add_month              integer,
  constraint pk_admin primary key (id)
);

create table admin_cart (
  id                            bigint auto_increment not null,
  admin_id                      integer,
  info_cart                     varchar(255),
  info_cart_wait_buy            varchar(255),
  time_change                   datetime(6),
  constraint pk_admin_cart primary key (id)
);

create table admin_emergency (
  id                            integer auto_increment not null,
  emergency_id                  bigint,
  admin_id                      integer,
  position_start                varchar(255),
  time_start_go                 bigint,
  time_start_job                bigint,
  time_end                      bigint,
  so_sao                        varchar(255),
  khach_hang_danh_gia           varchar(255),
  constraint pk_admin_emergency primary key (id)
);

create table admin_emergency_report (
  id                            integer auto_increment not null,
  admin_id                      integer,
  admin_emergency_id            integer,
  content                       varchar(255),
  emergency_id                  integer,
  created_at                    datetime(6),
  constraint pk_admin_emergency_report primary key (id)
);

create table admin_mission (
  id                            integer auto_increment not null,
  admin_id                      integer,
  service_package_maintenance_id integer,
  name                          varchar(255),
  position_start                varchar(255),
  time_start_go                 bigint,
  time_start_job                bigint,
  time_end                      bigint,
  so_sao                        varchar(255),
  khach_hang_danh_gia           varchar(255),
  constraint pk_admin_mission primary key (id)
);

create table admin_mission_report (
  id                            integer auto_increment not null,
  admin_id                      integer,
  admin_mission_id              integer,
  content                       varchar(255),
  service_package_user_id       integer,
  created_at                    datetime(6),
  constraint pk_admin_mission_report primary key (id)
);

create table admin_require_payment (
  id                            bigint auto_increment not null,
  admin_id                      bigint,
  level                         integer,
  type                          integer,
  balance_wait_payment          integer,
  bonus_wait_payment            integer,
  bonus_introduce_customer_wait_payment integer,
  vnd                           bigint,
  conversion_rate               varchar(255),
  total_money_payment           bigint,
  account_bank_admin_id         bigint,
  description                   varchar(255),
  bank_info                     varchar(255),
  status                        integer,
  created                       datetime(6),
  constraint pk_admin_require_payment primary key (id)
);

create table alert (
  id                            integer auto_increment not null,
  message                       varchar(255),
  constraint pk_alert primary key (id)
);

create table app_info (
  link_app                      varchar(255),
  link_fanpage                  varchar(255),
  version                       varchar(255),
  show_shop                     integer,
  show_recharge                 integer,
  show_register_ctv             integer,
  show_tim_tho_quanh_day        integer
);

create table appliance (
  id                            integer auto_increment not null,
  name                          varchar(255),
  manufacturer                  varchar(255),
  model                         varchar(255),
  quantity                      integer,
  time                          bigint,
  type                          integer,
  service_package_user_id       integer,
  status                        integer,
  constraint pk_appliance primary key (id)
);

create table bank_info (
  id                            integer auto_increment not null,
  code                          varchar(255),
  bank_name                     varchar(255),
  status                        integer,
  link_icon                     varchar(255),
  constraint pk_bank_info primary key (id)
);

create table banner (
  id                            integer,
  img                           varchar(255),
  route                         varchar(255),
  name                          varchar(255),
  description                   varchar(255)
);

create table ccu_log (
  id                            integer auto_increment not null,
  ccu                           integer not null,
  ccu_users                     integer not null,
  ccu_ktv                       integer not null,
  ccu_cskh                      integer not null,
  ccu_ctv                       integer not null,
  ccu_info                      varchar(255),
  action_user                   varchar(255),
  time                          varchar(255),
  date                          varchar(255),
  constraint pk_ccu_log primary key (id)
);

create table chat_info (
  id                            bigint auto_increment not null,
  type_account                  integer,
  account_id                    integer,
  account_id_chat               integer,
  content                       varchar(255),
  id_cskh                       integer,
  fullname                      varchar(255),
  type_account_chat             integer,
  is_read                       integer,
  created_time                  datetime(6),
  constraint pk_chat_info primary key (id)
);

create table cms_add_money_logs (
  id                            integer auto_increment not null,
  type                          integer,
  userid                        integer,
  note                          varchar(255),
  money_id                      integer,
  money                         bigint,
  sub                           integer,
  created_at                    datetime(6),
  admin_name                    varchar(255),
  constraint pk_cms_add_money_logs primary key (id)
);

create table cms_config_payment (
  id                            integer auto_increment not null,
  id_money                      integer,
  area_id                       integer,
  time                          integer,
  limit                         integer,
  tyle                          float,
  admin_id                      varchar(255),
  constraint pk_cms_config_payment primary key (id)
);

create table collaborator_mission (
  id                            bigint auto_increment not null,
  user_id                       integer,
  service_id                    integer,
  phone                         varchar(255),
  address                       varchar(255),
  details                       varchar(255),
  images                        varchar(255),
  latitude                      double,
  longitude                     double,
  status                        integer,
  send_ktv                      integer,
  admin_id                      integer,
  evaluate_ctv                  integer,
  created                       datetime(6),
  note                          varchar(255),
  reason_cancel                 varchar(255),
  constraint pk_collaborator_mission primary key (id)
);

create table collaborator_mission_report (
  id                            bigint auto_increment not null,
  admin_id                      integer,
  collaborator_mission_id       bigint,
  content                       varchar(255),
  created_at                    datetime(6),
  constraint pk_collaborator_mission_report primary key (id)
);

create table collaborator_register (
  id                            integer auto_increment not null,
  user_id                       integer,
  fullname                      varchar(255),
  birthday                      varchar(255),
  phone                         varchar(255),
  address                       varchar(255),
  works                         varchar(255),
  status                        integer,
  admin_check                   varchar(255),
  created_at                    datetime(6),
  constraint pk_collaborator_register primary key (id)
);

create table config_payment (
  id                            integer auto_increment not null,
  so_du_balance                 integer,
  so_du_bonus                   integer,
  so_du_bonus_introduce_customer integer,
  min_total_payment             integer,
  constraint pk_config_payment primary key (id)
);

create table config_server (
  id                            integer auto_increment not null,
  link_url_recharge_card        varchar(255),
  partner_card                  varchar(255),
  password_partner_card         varchar(255),
  epay_link_url_recharge        varchar(255),
  epay_merchant_code            varchar(255),
  epay_merchant_send_key        varchar(255),
  epay_merchant_receive_key     varchar(255),
  epay_issuer_id                varchar(255),
  epay_response_link            varchar(255),
  key_firebase                  varchar(255),
  money_register_account        bigint,
  money_thuong_nhap_ma_gioi_thieu bigint,
  ip_server                     varchar(255),
  link_port_3000                varchar(255),
  link_port_90                  varchar(255),
  link_port_91                  varchar(255),
  link_port_93                  varchar(255),
  nl_url_recharge               varchar(255),
  nl_merchant_id_send           varchar(255),
  nl_merchant_password_send     varchar(255),
  nl_merchant_id_check          varchar(255),
  nl_merchant_password_check    varchar(255),
  nl_receiver_email             varchar(255),
  nl_return_url                 varchar(255),
  nl_cancel_url                 varchar(255),
  time_write_ccu_log            integer,
  gmail_send                    varchar(255),
  gmail_password                varchar(255),
  constraint pk_config_server primary key (id)
);

create table coupon (
  id                            bigint auto_increment not null,
  type                          integer,
  coupon                        varchar(255),
  description                   varchar(255),
  percent                       float,
  money                         integer,
  han_muc                       integer,
  used                          integer,
  sender                        varchar(255),
  created_at                    datetime(6),
  constraint pk_coupon primary key (id)
);

create table emergency (
  id                            bigint auto_increment not null,
  user_id                       integer,
  service_package_user_id       integer,
  phone                         varchar(255),
  des                           varchar(255),
  des_cancel                    varchar(255),
  address                       varchar(255),
  latitude                      double,
  longitude                     double,
  images                        varchar(255),
  status                        integer,
  partner_id                    varchar(255),
  evaluate_partner              integer,
  bonus_ktv                     integer,
  time                          bigint,
  note                          varchar(255),
  constraint pk_emergency primary key (id)
);

create table evaluate_ctv (
  id                            bigint auto_increment not null,
  user_id                       integer,
  admin_id                      integer,
  service_id                    integer,
  collaborator_id               bigint,
  so_sao                        integer,
  content                       varchar(255),
  created                       datetime(6),
  constraint pk_evaluate_ctv primary key (id)
);

create table gift_code (
  id                            bigint auto_increment not null,
  code                          varchar(255),
  type                          integer,
  div                           bigint,
  service_package_id            integer,
  agent                         varchar(255),
  expire_date                   datetime(6),
  use_date                      datetime(6),
  use_by                        integer,
  create_time                   datetime(6),
  create_by                     bigint,
  area_id                       varchar(255),
  service_package_user_id       integer,
  constraint pk_gift_code primary key (id)
);

create table information (
  id                            integer auto_increment not null,
  title                         varchar(255),
  intro                         varchar(255),
  content                       varchar(255),
  type                          integer,
  created                       bigint,
  link                          varchar(255),
  img                           varchar(255),
  constraint pk_information primary key (id)
);

create table introduction (
  id                            integer auto_increment not null,
  link                          varchar(255),
  constraint pk_introduction primary key (id)
);

create table log_buy_service_package_user (
  id                            bigint auto_increment not null,
  user_id                       integer,
  service_package_id            integer,
  created                       datetime(6),
  constraint pk_log_buy_service_package_user primary key (id)
);

create table log_enter_introduction_code (
  id                            integer auto_increment not null,
  user_id                       integer,
  recipient_id                  integer,
  recipient_type                integer,
  money                         bigint,
  created_at                    datetime(6),
  constraint pk_log_enter_introduction_code primary key (id)
);

create table log_ktv_confirm_recharge_from_user (
  id                            bigint auto_increment not null,
  admin_id                      integer,
  balance_tru                   integer,
  so_tien_da_tru_am             integer,
  description                   varchar(255),
  created_at                    datetime(6),
  constraint pk_log_ktv_confirm_recharge_from_user primary key (id)
);

create table log_payment_cart_admin (
  id                            bigint auto_increment not null,
  admin_id                      integer,
  price                         bigint,
  product_id_detail             varchar(255),
  detail_cart                   varchar(255),
  status                        integer,
  coupon_id                     bigint,
  nguoi_giao                    varchar(255),
  detail_step                   varchar(255),
  created                       datetime(6),
  constraint pk_log_payment_cart_admin primary key (id)
);

create table log_payment_cart_user (
  id                            bigint auto_increment not null,
  user_id                       integer,
  price                         bigint,
  product_id_detail             varchar(255),
  detail_cart                   varchar(255),
  status                        integer,
  coupon_id                     bigint,
  nguoi_giao                    varchar(255),
  detail_step                   varchar(255),
  created                       datetime(6),
  constraint pk_log_payment_cart_user primary key (id)
);

create table log_product_gift_user_used (
  id                            integer auto_increment not null,
  product_gift_id               integer,
  user_id                       integer,
  used                          integer,
  created                       datetime(6),
  constraint pk_log_product_gift_user_used primary key (id)
);

create table log_product_reward_points_user (
  id                            bigint auto_increment not null,
  product_reward_points_id      integer,
  user_id                       integer,
  created                       datetime(6),
  constraint pk_log_product_reward_points_user primary key (id)
);

create table log_recharge_epay (
  id                            bigint auto_increment not null,
  type                          integer,
  type_account                  integer,
  account_id                    integer,
  tien_nap                      bigint,
  fee                           varchar(255),
  username_send_nap             varchar(255),
  issuer_id                     varchar(255),
  transaction_id                varchar(255),
  bank_code                     varchar(255),
  bank_name                     varchar(255),
  is_add_money                  integer,
  temp_send                     varchar(255),
  mac_send                      varchar(255),
  mac_receive                   varchar(255),
  status                        integer,
  response_send                 varchar(255),
  link_checkout                 varchar(255),
  response_receive              varchar(255),
  description_send              varchar(255),
  description_receive           varchar(255),
  created_at                    datetime(6),
  constraint pk_log_recharge_epay primary key (id)
);

create table log_recharge_ngan_luong (
  id                            bigint auto_increment not null,
  type                          integer,
  type_account                  integer,
  account_id                    integer,
  tien_nap                      integer,
  fee                           varchar(255),
  username_send_nap             varchar(255),
  merchant_id                   varchar(255),
  transaction_id                varchar(255),
  bank_code                     varchar(255),
  bank_name                     varchar(255),
  is_add_money                  integer,
  temp_send                     varchar(255),
  status                        integer,
  response_send                 varchar(255),
  link_checkout                 varchar(255),
  token                         varchar(255),
  response_receive              varchar(255),
  description_send              varchar(255),
  description_receive           varchar(255),
  order_code                    varchar(255),
  order_id                      varchar(255),
  transaction_status            varchar(255),
  transaction_id_ngan_luong     varchar(255),
  created_at                    datetime(6),
  constraint pk_log_recharge_ngan_luong primary key (id)
);

create table log_recharge_via_ktv (
  id                            bigint auto_increment not null,
  user_id                       integer,
  money                         integer,
  description                   varchar(255),
  description_response          varchar(255),
  admin_id                      integer,
  status                        integer,
  is_add_money                  integer,
  created_at                    datetime(6),
  constraint pk_log_recharge_via_ktv primary key (id)
);

create table money_log (
  id                            bigint auto_increment not null,
  user_id                       bigint,
  username                      varchar(255),
  money_old                     bigint,
  money_new                     bigint,
  money_update                  bigint,
  description                   varchar(255),
  login_time                    datetime(6),
  logout_time                   datetime(6),
  type                          integer,
  constraint pk_money_log primary key (id)
);

create table notify (
  id                            bigint auto_increment not null,
  sender                        varchar(255),
  type_account                  integer,
  account_id                    bigint,
  title                         varchar(255),
  content                       varchar(255),
  is_read                       integer,
  is_delete                     integer,
  created                       datetime(6),
  constraint pk_notify primary key (id)
);

create table partner (
  id                            integer auto_increment not null,
  username                      varchar(255),
  password                      varchar(255),
  fullname                      varchar(255),
  phone                         varchar(255),
  address                       varchar(255),
  rank                          integer,
  balance                       bigint,
  bonus                         bigint,
  status                        integer,
  created                       bigint,
  create_by                     integer,
  manager                       integer,
  group_id                      integer,
  constraint pk_partner primary key (id)
);

create table pay_cards (
  id                            bigint auto_increment not null,
  game_id                       integer,
  trans_id                      varchar(255),
  session_id                    varchar(255),
  user_id                       bigint,
  username                      varchar(255),
  request                       varchar(255),
  response                      varchar(255),
  requested_at                  datetime(6),
  responsed_at                  datetime(6),
  status                        integer,
  created_at                    datetime(6),
  response_status               integer,
  provider_code                 varchar(255),
  card_code                     varchar(255),
  card_seri                     varchar(255),
  price                         integer,
  conversion_price              integer,
  provider_id                   integer,
  constraint pk_pay_cards primary key (id)
);

create table product (
  id                            integer auto_increment not null,
  type                          integer,
  name                          varchar(255),
  descriptions                  varchar(255),
  link_icon                     varchar(255),
  price                         bigint,
  sale                          float,
  is_new                        integer,
  shop                          integer,
  number                        integer,
  is_show                       integer,
  created                       bigint,
  constraint pk_product primary key (id)
);

create table product_gift (
  id                            integer auto_increment not null,
  type                          integer,
  title                         varchar(255),
  description                   varchar(255),
  time_event_start              datetime(6),
  time_event_stop               datetime(6),
  number_day_use                integer,
  product_id                    integer,
  number                        integer,
  service_package_id            integer,
  constraint pk_product_gift primary key (id)
);

create table product_ktv (
  id                            bigint auto_increment not null,
  admin_id                      integer,
  product_id                    integer,
  number                        integer,
  constraint pk_product_ktv primary key (id)
);

create table product_ktv_log_buy (
  id                            bigint auto_increment not null,
  admin_id                      integer,
  product_id                    integer,
  number                        integer,
  product_type                  integer,
  money                         bigint,
  sale                          float,
  created                       bigint,
  constraint pk_product_ktv_log_buy primary key (id)
);

create table product_ktv_log_use (
  id                            bigint auto_increment not null,
  admin_id                      integer,
  product_id                    integer,
  number                        integer,
  created                       bigint,
  constraint pk_product_ktv_log_use primary key (id)
);

create table product_reward_points (
  id                            integer auto_increment not null,
  description                   varchar(255),
  product_id                    integer,
  number                        integer,
  price                         integer,
  is_show                       integer,
  constraint pk_product_reward_points primary key (id)
);

create table product_type (
  id                            integer auto_increment not null,
  name                          varchar(255),
  constraint pk_product_type primary key (id)
);

create table product_user (
  id                            bigint auto_increment not null,
  user_id                       integer,
  product_id                    integer,
  number                        integer,
  constraint pk_product_user primary key (id)
);

create table product_user_log_buy (
  id                            bigint auto_increment not null,
  user_id                       integer,
  product_id                    integer,
  number                        integer,
  product_type                  integer,
  money                         bigint,
  sale                          float,
  created                       bigint,
  constraint pk_product_user_log_buy primary key (id)
);

create table product_user_log_use (
  id                            bigint auto_increment not null,
  user_id                       integer,
  product_id                    integer,
  number                        integer,
  created                       bigint,
  constraint pk_product_user_log_use primary key (id)
);

create table question (
  id                            integer auto_increment not null,
  question                      varchar(255),
  answer                        varchar(255),
  constraint pk_question primary key (id)
);

create table read_notify_system (
  id                            bigint auto_increment not null,
  type_account                  integer,
  account_id                    integer,
  notify_system                 varchar(255),
  constraint pk_read_notify_system primary key (id)
);

create table service (
  id                            integer auto_increment not null,
  name                          varchar(255),
  link_icon                     varchar(255),
  link_image                    varchar(255),
  description                   varchar(255),
  constraint pk_service primary key (id)
);

create table service_info (
  id                            integer auto_increment not null,
  name                          varchar(255),
  des                           varchar(255),
  link_icon                     varchar(255),
  service_package_id            integer,
  constraint pk_service_info primary key (id)
);

create table service_package (
  id                            integer auto_increment not null,
  parent_id                     integer,
  name                          varchar(255),
  time_id                       integer,
  square_id                     integer,
  des                           varchar(255),
  price                         integer,
  sale                          float,
  icon                          varchar(255),
  number_maintenance            integer,
  benefit                       varchar(255),
  constraint pk_service_package primary key (id)
);

create table service_package_maintenance (
  id                            integer auto_increment not null,
  service_package_user_id       integer,
  des                           varchar(255),
  status                        integer,
  time                          bigint,
  admin_id                      varchar(255),
  number                        integer,
  des_cancel                    varchar(255),
  evaluate_admin                integer,
  bonus_ktv                     integer,
  constraint pk_service_package_maintenance primary key (id)
);

create table service_package_square (
  id                            integer auto_increment not null,
  name                          varchar(255),
  constraint pk_service_package_square primary key (id)
);

create table service_package_temp_address (
  id                            integer auto_increment not null,
  address                       varchar(255),
  province_id                   varchar(255),
  district_id                   varchar(255),
  ward_id                       varchar(255),
  latitude                      double,
  longitude                     double,
  service_package_user_id       integer,
  create_at                     datetime(6),
  status                        integer,
  type                          integer,
  confirm_by                    integer,
  constraint pk_service_package_temp_address primary key (id)
);

create table service_package_time (
  id                            integer auto_increment not null,
  name                          varchar(255),
  time                          bigint,
  constraint pk_service_package_time primary key (id)
);

create table service_package_user (
  id                            integer auto_increment not null,
  user_id                       integer,
  service_package_id            integer,
  start_time                    bigint,
  end_time                      bigint,
  address                       varchar(255),
  latitude                      double,
  longitude                     double,
  province_id                   varchar(255),
  district_id                   varchar(255),
  ward_id                       varchar(255),
  constraint pk_service_package_user primary key (id)
);

create table sg_admin (
  id                            integer auto_increment not null,
  command                       varchar(255),
  status                        varchar(255),
  constraint pk_sg_admin primary key (id)
);

create table transaction_history_admin (
  id                            bigint auto_increment not null,
  admin_id                      integer,
  type                          integer,
  transaction_id                bigint,
  status                        integer,
  price                         bigint,
  descriptions                  varchar(255),
  created_time                  datetime(6),
  constraint pk_transaction_history_admin primary key (id)
);

create table transaction_history_user (
  id                            bigint auto_increment not null,
  user_id                       integer,
  type                          integer,
  transaction_id                bigint,
  status                        integer,
  price                         bigint,
  descriptions                  varchar(255),
  created_time                  datetime(6),
  constraint pk_transaction_history_user primary key (id)
);

create table type_card (
  id                            integer auto_increment not null,
  name_card                     varchar(255),
  provider_code                 varchar(255),
  status                        integer,
  type_card                     integer,
  link_icon_card                varchar(255),
  constraint pk_type_card primary key (id)
);

create table user (
  id                            integer auto_increment not null,
  username                      varchar(255),
  password                      varchar(255),
  fullname                      varchar(255),
  phone                         varchar(255),
  email                         varchar(255),
  address                       varchar(255),
  rank                          integer,
  score                         integer,
  balance                       bigint,
  reward_point                  integer,
  status                        integer,
  created_at                    datetime(6),
  fcm_token                     varchar(255),
  link_avatar                   varchar(255),
  device                        integer,
  total_money_charging_card     bigint,
  total_money_charging_bank     bigint,
  province                      varchar(255),
  district                      varchar(255),
  ward                          varchar(255),
  province_id                   varchar(255),
  district_id                   varchar(255),
  ward_id                       varchar(255),
  first_login                   integer,
  birthday                      varchar(255),
  cancel_call_ctv               integer,
  cancel_call_emergency         integer,
  ip_current                    varchar(255),
  imei                          varchar(255),
  add_money_register            integer,
  latitude                      double,
  longitude                     double,
  constraint pk_user primary key (id)
);

create table user_cart (
  id                            integer auto_increment not null,
  user_id                       integer,
  info_cart                     varchar(255),
  info_cart_wait_buy            varchar(255),
  time_change                   datetime(6),
  constraint pk_user_cart primary key (id)
);

create table user_reset_password (
  id                            integer auto_increment not null,
  username                      varchar(255),
  email                         varchar(255),
  otp                           varchar(255),
  expired_time                  datetime(6),
  constraint pk_user_reset_password primary key (id)
);

create table user_temp_address (
  id                            integer auto_increment not null,
  user_id                       integer,
  address                       varchar(255),
  province                      varchar(255),
  district                      varchar(255),
  ward                          varchar(255),
  province_id                   varchar(255),
  district_id                   varchar(255),
  ward_id                       varchar(255),
  latitude                      double,
  longitude                     double,
  constraint pk_user_temp_address primary key (id)
);

create table vn_city (
  id                            varchar(255) not null,
  name                          varchar(255),
  type                          varchar(255),
  area                          integer,
  constraint pk_vn_city primary key (id)
);

create table vn_district (
  id                            varchar(255) not null,
  name                          varchar(255),
  type                          varchar(255),
  city_id                       varchar(255),
  constraint pk_vn_district primary key (id)
);

create table vn_ward (
  id                            varchar(255) not null,
  name                          varchar(255),
  type                          varchar(255),
  district_id                   varchar(255),
  constraint pk_vn_ward primary key (id)
);


# --- !Downs

drop table if exists account_bank_admin;

drop table if exists admin;

drop table if exists admin_cart;

drop table if exists admin_emergency;

drop table if exists admin_emergency_report;

drop table if exists admin_mission;

drop table if exists admin_mission_report;

drop table if exists admin_require_payment;

drop table if exists alert;

drop table if exists app_info;

drop table if exists appliance;

drop table if exists bank_info;

drop table if exists banner;

drop table if exists ccu_log;

drop table if exists chat_info;

drop table if exists cms_add_money_logs;

drop table if exists cms_config_payment;

drop table if exists collaborator_mission;

drop table if exists collaborator_mission_report;

drop table if exists collaborator_register;

drop table if exists config_payment;

drop table if exists config_server;

drop table if exists coupon;

drop table if exists emergency;

drop table if exists evaluate_ctv;

drop table if exists gift_code;

drop table if exists information;

drop table if exists introduction;

drop table if exists log_buy_service_package_user;

drop table if exists log_enter_introduction_code;

drop table if exists log_ktv_confirm_recharge_from_user;

drop table if exists log_payment_cart_admin;

drop table if exists log_payment_cart_user;

drop table if exists log_product_gift_user_used;

drop table if exists log_product_reward_points_user;

drop table if exists log_recharge_epay;

drop table if exists log_recharge_ngan_luong;

drop table if exists log_recharge_via_ktv;

drop table if exists money_log;

drop table if exists notify;

drop table if exists partner;

drop table if exists pay_cards;

drop table if exists product;

drop table if exists product_gift;

drop table if exists product_ktv;

drop table if exists product_ktv_log_buy;

drop table if exists product_ktv_log_use;

drop table if exists product_reward_points;

drop table if exists product_type;

drop table if exists product_user;

drop table if exists product_user_log_buy;

drop table if exists product_user_log_use;

drop table if exists question;

drop table if exists read_notify_system;

drop table if exists service;

drop table if exists service_info;

drop table if exists service_package;

drop table if exists service_package_maintenance;

drop table if exists service_package_square;

drop table if exists service_package_temp_address;

drop table if exists service_package_time;

drop table if exists service_package_user;

drop table if exists sg_admin;

drop table if exists transaction_history_admin;

drop table if exists transaction_history_user;

drop table if exists type_card;

drop table if exists user;

drop table if exists user_cart;

drop table if exists user_reset_password;

drop table if exists user_temp_address;

drop table if exists vn_city;

drop table if exists vn_district;

drop table if exists vn_ward;

