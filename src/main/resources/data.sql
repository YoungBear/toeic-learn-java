-- 托业核心词汇示例数据
-- 先清空已存在的数据避免重复
DELETE FROM learning_progress WHERE id > 0;
DELETE FROM practice_record WHERE id > 0;
DELETE FROM word WHERE id > 0;
DELETE FROM vocabulary WHERE id > 0;

-- 词汇书1: 商务基础词汇
INSERT INTO vocabulary (id, name, description) VALUES (1, '商务基础词汇', '托业考试商务场景常用基础词汇');

-- 词汇书2: 日常交流词汇
INSERT INTO vocabulary (id, name, description) VALUES (2, '日常交流词汇', '日常生活和工作中常用交流词汇');

-- 词汇书3: 会议与谈判
INSERT INTO vocabulary (id, name, description) VALUES (3, '会议与谈判', '会议、谈判、商务讨论相关词汇');

-- 商务基础词汇 (vocabulary_id = 1)
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'agreement', '协议;合同', '/əˈɡriːmənt/', 'We reached an agreement on the terms.', 2, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'contract', '合同;契约', '/ˈkɒntrækt/', 'The company signed a contract with the supplier.', 2, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'negotiate', '谈判;协商', '/nɪˈɡəʊʃieɪt/', 'We need to negotiate a better price.', 3, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'proposal', '提案;建议', '/prəˈpəʊzl/', 'She submitted a proposal for the new project.', 2, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'budget', '预算', '/ˈbʌdʒɪt/', 'The department exceeded its budget this quarter.', 1, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'revenue', '收入;收益', '/ˈrevənjuː/', 'The company revenue increased by 15%.', 3, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'profit', '利润;盈利', '/ˈprɒfɪt/', 'They made a profit of $1 million.', 1, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'investment', '投资', '/ɪnˈvestmənt/', 'Foreign investment is encouraged.', 2, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'strategy', '策略;战略', '/ˈstrætədʒi/', 'We need a new marketing strategy.', 2, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'deadline', '截止日期', '/ˈdedlaɪn/', 'The deadline for the report is Friday.', 1, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'quarter', '季度', '/ˈkwɔːtər/', 'Q3 quarter results were positive.', 2, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'forecast', '预测;预报', '/ˈfɔːkɑːst/', 'Sales forecast looks promising.', 3, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'schedule', '日程安排', '/ˈʃedjuːl/', 'The meeting is on the schedule.', 1, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'attendee', '参加者', '/ətenˈdiː/', 'There are 20 attendees at the conference.', 3, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'presentation', '演示;报告', '/ˌprezənˈteɪʃn/', 'The presentation was very informative.', 2, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'conference', '会议;大会', '/ˈkɒnfərəns/', 'The international conference begins tomorrow.', 1, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'survey', '调查;问卷', '/ˈsɜːrveɪ/', 'We conducted a customer survey.', 2, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'feedback', '反馈', '/ˈfiːdbæk/', 'Please provide your feedback on the proposal.', 1, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'implement', '实施;执行', '/ˈɪmplɪment/', 'We will implement the new policy next month.', 3, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'evaluate', '评估;评价', '/ɪˈvæljueɪt/', 'The committee will evaluate the applications.', 3, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'benefit', '利益;好处', '/ˈbenɪfɪt/', 'The project has many benefits.', 1, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'decision', '决定;决策', '/dɪˈsɪʒn/', 'We need to make a decision today.', 1, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'opportunity', '机会', '/ˌɒpərˈtjuːnɪti/', 'This is a great opportunity for growth.', 2, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'issue', '问题;发行', '/ˈɪʃuː/', 'There is an issue that needs to be addressed.', 1, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'achieve', '达到;实现', '/əˈtʃiːv/', 'We achieved our sales targets.', 2, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'confirm', '确认', '/kənˈfɜːrm/', 'Please confirm your attendance.', 1, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'approve', '批准', '/əˈpruːv/', 'The board approved the merger.', 2, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'department', '部门', '/dɪˈpɑːrtmənt/', 'The marketing department is hiring.', 1, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'employee', '员工', '/ɪmˈplɔɪiː/', 'The company has 500 employees.', 1, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (1, 'manager', '经理;管理者', '/ˈmænɪdʒər/', 'The manager approved the leave request.', 1, 'noun', false);

-- 日常交流词汇 (vocabulary_id = 2)
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'appointment', '约会;预约', '/əˈpɔɪntmənt/', 'I have an appointment at 3 PM.', 1, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'schedule', '日程;schedule', '/ˈʃedjuːl/', 'My schedule is very busy today.', 1, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'available', '有空的;可获得的', '/əˈveɪləbl/', 'Are you available for a meeting tomorrow?', 1, 'adj', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'confirm', '确认', '/kənˈfɜːrm/', 'Let me confirm the details.', 1, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'reschedule', '重新安排', '/riːˈʃedjuːl/', 'Can we reschedule our meeting?', 2, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'coordinate', '协调', '/kəʊˈɔːrdɪneɪt/', 'We need to coordinate our efforts.', 3, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'communicate', '沟通;交流', '/kəˈmjuːnɪkeɪt/', 'Good communication is key.', 2, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'discuss', '讨论', '/dɪˈskʌs/', 'We will discuss this matter later.', 1, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'explain', '解释', '/ɪkˈspleɪn/', 'Could you explain the process?', 1, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'clarify', '澄清;说明', '/ˈklærəfaɪ/', 'Please clarify your requirements.', 2, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'understand', '理解', '/ˌʌndərˈstænd/', 'I understand your concern.', 1, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'suggest', '建议', '/səˈdʒest/', 'I suggest we start early.', 2, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'recommend', '推荐', '/ˌrekəˈmend/', 'I recommend this restaurant.', 2, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'arrange', '安排', '/əˈreɪndʒ/', 'I will arrange the transportation.', 2, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'organize', '组织;安排', '/ˈɔːrɡənaɪz/', 'Let me organize the files.', 2, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'cancel', '取消', '/ˈkænsl/', 'The meeting was cancelled.', 1, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'postpone', '推迟', '/pəˈspəʊn/', 'The event has been postponed.', 2, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'update', '更新', '/ˌʌpˈdeɪt/', 'I will update you on the progress.', 1, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'inform', '通知', '/ɪnˈfɔːrm/', 'Please inform all staff members.', 2, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'notify', '通知', '/ˈnəʊtɪfaɪ/', 'We will notify you when ready.', 2, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'reply', '回复', '/rɪˈplaɪ/', 'Please reply by Friday.', 1, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'respond', '回应;响应', '/rɪˈspɒnd/', 'Please respond to the email.', 1, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'contact', '联系', '/ˈkɒntækt/', 'I will contact you next week.', 1, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'follow up', '跟进', '/ˈfɒləʊ ʌp/', 'Let us follow up on this issue.', 2, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'introduce', '介绍', '/ˌɪntrəˈdjuːs/', 'Let me introduce my colleague.', 1, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'greet', '问候', '/ɡriːt/', 'We always greet customers warmly.', 1, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'schedule', '安排日程', '/ˈʃedjuːl/', 'She scheduled the meeting for 2 PM.', 1, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'flexible', '灵活的', '/ˈfleksəbl/', 'We need a flexible approach.', 2, 'adj', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'convenient', '方便的', '/kənˈviːniənt/', 'What time is convenient for you?', 2, 'adj', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (2, 'efficient', '高效的', '/ɪˈfɪʃnt/', 'This is a very efficient method.', 2, 'adj', false);

-- 会议与谈判 (vocabulary_id = 3)
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'agenda', '议程', '/əˈdʒendə/', 'The agenda includes three main topics.', 2, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'minutes', '会议记录', '/ˈmɪnɪts/', 'Please circulate the minutes after the meeting.', 2, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'chairman', '主席;主持人', '/ˈtʃeərmən/', 'The chairman opened the meeting.', 2, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'motion', '动议;提议', '/ˈməʊʃn/', 'Someone made a motion to adjourn.', 3, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'amendment', '修正案', '/əˈmendmənt/', 'An amendment was proposed.', 3, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'consensus', '共识', '/kənˈsensəs/', 'We reached a consensus on the plan.', 3, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'compromise', '妥协;折中', '/ˈkɒmprəmaɪz/', 'Both parties reached a compromise.', 2, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'bargain', '讨价还价', '/ˈbɑːrɡɪn/', 'We bargained with the supplier.', 2, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'concede', '让步;承认', '/kənˈsiːd/', 'They conceded the point.', 3, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'objection', '反对', '/əbˈdʒekʃn/', 'Is there any objection to this plan?', 2, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'approve', '批准', '/əˈpruːv/', 'The board approved the budget.', 2, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'reject', '拒绝', '/rɪˈdʒekt/', 'The proposal was rejected.', 2, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'accept', '接受', '/əkˈsept/', 'We accept your offer.', 1, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'decline', '拒绝;下降', '/dɪˈklaɪn/', 'We regret to decline your invitation.', 2, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'withdraw', '撤回;撤销', '/wɪðˈdrɔː/', 'They withdrew their offer.', 3, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'propose', '提议;建议', '/prəˈpəʊz/', 'I propose we take a vote.', 2, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'second', '附议', '/ˈsekənd/', 'I second the motion.', 2, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'adjourn', '休会', '/əˈdʒɜːrn/', 'The meeting was adjourned.', 3, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'convene', '召集;召开', '/kənˈviːn/', 'The board will convene next week.', 3, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'clause', '条款', '/klɔːz/', 'There is a penalty clause in the contract.', 3, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'term', '条款;术语', '/tɜːrm/', 'We agreed on the terms of the deal.', 1, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'condition', '条件', '/kənˈdɪʃn/', 'Under this condition, we accept.', 2, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'provision', '规定;条款', '/prəˈvɪʒn/', 'The contract includes a confidentiality provision.', 3, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'witness', '见证人;见证', '/ˈwɪtnəs/', 'The contract was signed in witness of.', 2, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'signature', '签名', '/ˈsɪɡnətʃər/', 'Please provide your signature.', 1, 'noun', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'execute', '签署;执行', '/ˈeksɪkjuːt/', 'The contract was executed yesterday.', 3, 'verb', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'binding', '具有约束力的', '/ˈbaɪndɪŋ/', 'The agreement is legally binding.', 3, 'adj', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'valid', '有效的', '/ˈvælɪd/', 'The contract remains valid for one year.', 2, 'adj', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'void', '无效的', '/vɔɪd/', 'The contract was declared void.', 3, 'adj', false);
INSERT INTO word (vocabulary_id, english, chinese, phonetic, example_sentence, difficulty, part_of_speech, favorited) VALUES (3, 'obligate', '使负有义务', '/ˈɒblɪɡeɪt/', 'We are obligated to fulfill the terms.', 3, 'verb', false);

-- 学习进度表初始化 (所有单词初始进度)
INSERT INTO learning_progress (word_id, mastery_level, last_practiced_at, next_review_at)
SELECT id, 0, NULL, CURRENT_TIMESTAMP FROM word WHERE id NOT IN (SELECT DISTINCT word_id FROM learning_progress);
