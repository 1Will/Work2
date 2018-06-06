---------------------------------------------------
-- Export file for user DEMO@ORCL                --
-- Created by Administrator on 2018-6-1, 8:59:46 --
---------------------------------------------------

set define off
spool jfyth.log

prompt
prompt Creating sequence SEQ_XT_CD
prompt ===========================
prompt
create sequence SEQ_XT_CD
minvalue 1
maxvalue 99999999999999999999
start with 67
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_XT_FJ
prompt ===========================
prompt
create sequence SEQ_XT_FJ
minvalue 1
maxvalue 99999999999999999999
start with 361
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_XT_JG
prompt ===========================
prompt
create sequence SEQ_XT_JG
minvalue 1
maxvalue 99999999999999999999
start with 20040
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_XT_JS
prompt ===========================
prompt
create sequence SEQ_XT_JS
minvalue 1
maxvalue 99999999999999999999
start with 204
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_XT_JSCD
prompt =============================
prompt
create sequence SEQ_XT_JSCD
minvalue 1
maxvalue 9999999999999999999999999999
start with 3990
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_XT_RZ
prompt ===========================
prompt
create sequence SEQ_XT_RZ
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_XT_SQ
prompt ===========================
prompt
create sequence SEQ_XT_SQ
minvalue 1
maxvalue 99999999999999999999999
start with 102
increment by 1
cache 10;

prompt
prompt Creating sequence SEQ_XT_SQJG
prompt =============================
prompt
create sequence SEQ_XT_SQJG
minvalue 1
maxvalue 9999999999999999999999999999
start with 241
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_XT_XX
prompt ===========================
prompt
create sequence SEQ_XT_XX
minvalue 1
maxvalue 99999999999999999999
start with 161
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_XT_YH
prompt ===========================
prompt
create sequence SEQ_XT_YH
minvalue 1
maxvalue 99999999999999999999
start with 145
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_XT_YHJS
prompt =============================
prompt
create sequence SEQ_XT_YHJS
minvalue 1
maxvalue 9999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_XT_ZD
prompt ===========================
prompt
create sequence SEQ_XT_ZD
minvalue 1
maxvalue 9999999999999999999999999999
start with 21
increment by 1
cache 20;


spool off
